package Views.HomeViews;

import Controller.DatabaseAPI;
import Controller.FormatData;
import Model.Deck;
import Model.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;


import Views.Components.Panel;
import Views.Components.Label;
import Views.Components.Button;
import Views.Components.TextField;
import Views.MainGUI;
import Views.MasterGUI;

public class DeckView extends Panel{

    /** Card layout for upcoming events */
    public static final int CREATE  = 0;

    /** Card layout for all events. Unlocks edit and delete options */
    public static final int EDIT  = 1;

    private static JFrame frame;
    private static JScrollPane scroller;
    private static Panel deckPanel;
    private static Panel editPanel;

    private static Label addLabel;
    private static Button addBtn;
    private static Button createBtn;
    private static Button deleteBtn;
    private static Button editBtn;
    private static Button saveBtn;
    private static Button cnclBtn;
    private static TextField titleField;
    private static JTextArea tagField;


    private int width;
    private int height;
    private int editWidth;

    private int currentDeckID = -1;

    private ArrayList<Deck> decks;
    private User user;

    /** Boolean to determine if text fields are editable or not */
    private boolean isEditable;

    public DeckView(JFrame frame, User user){
        super(frame);
        this.frame = frame;
        this.deckPanel = new Panel();
        this.editPanel = new Panel();

        this.width = frame.getWidth();
        this.height = frame.getHeight();
        this.editWidth = width/2;

        this.user = user;
        this.decks = user.getDecks();


        editPanel.setBounds(frame.getWidth()/2, 0, frame.getWidth()/2, frame.getHeight());
        editPanel.setBackground(MasterGUI.black_gray);
        editPanel.setOpaque(true);

        deckPanel.setPreferredSize(new Dimension(frame.getWidth()/2 , frame.getHeight()*2+50));
        deckPanel.setBackground(MasterGUI.babyblue);

        drawLeftSide();
        initEditFields();
        initEditButtons();
        initAddBtn();
        initCreateDeckBtn();


        scroller = makeScroller(deckPanel);
        add(scroller);
        this.add(editPanel);

        frame.repaint();

    }

    public void drawLeftSide() {
        int margin = 120;
        Point point = new Point(30,50);

        for(int i = 0; i < decks.size(); i++) {
            drawDeckCard(point, i,  deckPanel);
            point.y += margin;
        }
    }

    public void drawDeckCard(Point point, int i, Panel panel) {
        Deck deck = decks.get(i);

        Panel deckCard = new Panel();
        deckCard.setBounds(point.x, point.y, 450, 100);
        deckCard.setBackground(MasterGUI.white);
        deckCard.setBorder(BorderFactory.createLineBorder(Color.black));
        deckCard.setOpaque(true);

        Button cardBtn = new Button(0,0,"");
        cardBtn.setSize(deckCard.getWidth(), deckCard.getHeight());
        cardBtn.setFocusPainted(false);
        mouseActionCardBtn(cardBtn, deckCard);
        switchDeck(cardBtn, i);

        Label title = new Label(60, 8, deck.getDeckName(), MasterGUI.black, 24f);
        title.setSize(250, 25);

        Button viewBtn = new Button(325 , 25 ,  "VIEW", MasterGUI.yellow, 110, 40);
        viewBtn.setFont(MasterGUI.poppinsFontBig);
        viewBtn.setForeground(MasterGUI.black);
        viewBtn.setContentAreaFilled(true);
        viewBtn.setFocusPainted(false);
        viewBtn.addActionListener(e -> {
            Panel viewMode = new CardView(frame, deck,user);
            MainGUI.switchPanel(viewMode);
        });


        //Button selectBtn = new Button(point.x + 475 , point.y + 30 ,  "SELECT");

        Panel icon = new Panel();
        icon.setBounds( 5, 10 , 45, 45);
        icon.setBackground(MasterGUI.purple);

        deckCard.add(viewBtn);
        deckCard.add(cardBtn);
        deckCard.add(title);
        deckCard.add(icon);

        //panel.add(selectBtn);
        panel.add(deckCard);

        deckCard.repaint();
        panel.repaint();
        deckCard.revalidate();
        panel.revalidate();
    }

    public void mouseActionCardBtn(Button cardBtn, Panel deckCard) {
        MouseListener mouse = new MouseListener() {
            public void mouseClicked(MouseEvent e) { }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { deckCard.setBackground(MasterGUI.grey); }
            public void mouseExited(MouseEvent e) { deckCard.setBackground(MasterGUI.white); }
        };
        cardBtn.addMouseListener(mouse);
    }

    /**
     * This method displays the titlefield and tagfield on the editpanel
     * with the corresponding information by clicking on a deck card
     * @param cardBtn Button on which this methods perform
     * @param i Run variable to determine which deck is is
     */
    public void switchDeck(Button cardBtn, int i) {
        ActionListener change = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDeckID = i;
                Deck deck = decks.get(currentDeckID);

                titleField.setText(deck.getDeckName());
                titleField.setEditable(false);
                List<String> deckTags = new ArrayList<>(deck.getTags().values());
                tagField.setText("");
                for(String tag : deckTags) {
                    tagField.append("#" + tag + " ");
                }
                tagField.setEditable(false);
                editPanel.remove(saveBtn);
                editPanel.remove(cnclBtn);
                editPanel.remove(createBtn);
                editPanel.add(editBtn);
                editPanel.add(deleteBtn);
                editPanel.setComponentZOrder(editBtn, 0);
                editPanel.setComponentZOrder(deleteBtn, 1);
                editPanel.repaint();
                editPanel.revalidate();
            }
        };
        cardBtn.addActionListener(change);
    }


    private void initAddBtn() {
        addBtn = new Button(30, 7, "");
        addBtn.setContentAreaFilled(false);
        addBtn.setFocusPainted(false);
        addBtn.setSize(35,35);
        addBtn.setIcon(FormatData.resizeImageIcon(MasterGUI.addIcon, 0.4f));
        addBtn.addActionListener(e -> {
            toCreateMode();
        });
        deckPanel.add(addBtn);

        addLabel = new Label(70, 14,"Add new deck...", MasterGUI.black, 18f);
        deckPanel.add(addLabel);

    }

    private void toCreateMode() {
        titleField.setToEditMode();
        titleField.setText("");
        tagField.setEditable(true);
        tagField.setText("");
        editPanel.remove(editBtn);
        editPanel.remove(deleteBtn);
        editPanel.remove(cnclBtn);
        editPanel.add(createBtn);
        //editPanel.setComponentZOrder(createBtn, 0);
        editPanel.repaint();
        editPanel.revalidate();
    }

    /**
     * Initialize button for creating a new deck
     */
    private void initCreateDeckBtn() {
        createBtn = new Button((editWidth - 310) / 2, 400, "CREATE NEW DECK", MasterGUI.green, titleField.getWidth(), titleField.getHeight());
        createBtn.setFont(MasterGUI.poppinsFont);
        createBtn.setForeground(MasterGUI.white);
        createBtn.setContentAreaFilled(true);
        createBtn.setFocusPainted(false);
        ActionListener createAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDeckFromInput();
                user.updateDeckList();
                repaintDeckCards();
                HomeView.repaintHomeView();
            }
        };
        createBtn.addActionListener(e ->  {
            MainGUI.confirmDialog(createAction, "Create new Deck?");
        });
        editPanel.add(createBtn);
    }


    /**
     * Stores new Deck in database with information from user's input
     */
    private void createDeckFromInput() {

        if(titleField.getText().isBlank()) {
            MainGUI.confirmDialog("Deckname cannot be blank");
            return;
        }
        String deckName = titleField.getText();
        Deck deck = new Deck(deckName, 0,0,0,0,0);
        int deckID = DatabaseAPI.storeDeck(deck);
        DatabaseAPI.createUserDeckBridge(user.getId(), deckID);

        String[] tags = FormatData.formatHashtags(tagField.getText());
        for(String tag : tags) {
            if(!FormatData.isBlankString(tag)) {
                int tagID = DatabaseAPI.checkTag(tag);
                if(tagID != 0) {
                    DatabaseAPI.createDeckTagBridge(deckID, tagID);
                } else {
                    int newTagID = DatabaseAPI.storeTag(tag);
                    DatabaseAPI.createDeckTagBridge(deckID, newTagID);
                }
            }
        }


    }

    private void initEditButtons() {
        editBtn = new Button((editWidth - 310) / 2, 400, "EDIT DECK", MasterGUI.yellow, titleField.getWidth(), titleField.getHeight());
        editBtn.setContentAreaFilled(true);
        editBtn.setFocusPainted(false);
        editBtn.setFont(MasterGUI.poppinsFont);
        editBtn.setForeground(MasterGUI.black);
        editBtn.addActionListener(e -> {
            titleField.setToEditMode();
            tagField.setEditable(true);
            editPanel.remove(editBtn);
            editPanel.remove(deleteBtn);
            editPanel.add(cnclBtn);
            editPanel.add(saveBtn);
            editPanel.setComponentZOrder(saveBtn, 0);
            editPanel.setComponentZOrder(cnclBtn, 1);
            editPanel.repaint();
            editPanel.revalidate();
        });


        saveBtn = new Button((editWidth - 310) / 2, 400, "SAVE CHANGES", MasterGUI.green, titleField.getWidth(), titleField.getHeight());
        saveBtn.setContentAreaFilled(true);
        saveBtn.setFocusPainted(false);
        saveBtn.setFont(MasterGUI.poppinsFont);
        saveBtn.setForeground(MasterGUI.white);
        ActionListener saveChanges = saveChanges();
        saveBtn.addActionListener(e -> MainGUI.confirmDialog(saveChanges, "Save Changes?"));

        deleteBtn = new Button((editWidth - 310) / 2, 450, "DELETE DECK", MasterGUI.red, titleField.getWidth(), titleField.getHeight());
        deleteBtn.setContentAreaFilled(true);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setFont(MasterGUI.poppinsFont);
        deleteBtn.setForeground(MasterGUI.white);
        ActionListener deleteAction = deleteAction();
        deleteBtn.addActionListener(e -> MainGUI.confirmDialog(deleteAction, "Are sure that you want to delete?"));


        cnclBtn = new Button((editWidth - 310) / 2, 450, "CANCEL", MasterGUI.red, titleField.getWidth(), titleField.getHeight());
        cnclBtn.setContentAreaFilled(true);
        cnclBtn.setFocusPainted(false);
        cnclBtn.setFont(MasterGUI.poppinsFont);
        cnclBtn.setForeground(MasterGUI.white);
        cnclBtn.addActionListener(e -> {
            titleField.setEditable(false);
            tagField.setEditable(false);
            editPanel.remove(saveBtn);
            editPanel.remove(cnclBtn);
            editPanel.add(editBtn);
            editPanel.add(deleteBtn);
            editPanel.setComponentZOrder(editBtn, 0);
            editPanel.setComponentZOrder(deleteBtn, 1);
            editPanel.repaint();
            editPanel.revalidate();
        });


    }


    private ActionListener deleteAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentDeckID == -1) System.out.println("Not Deleted");
                else {
                    user.deleteDeck(decks.get(currentDeckID));
                    user.updateDeckList();
                    toCreateMode();
                    repaintDeckCards();
                    HomeView.repaintHomeView();
                }
            }
        };
    }

    private ActionListener saveChanges() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentDeckID == -1) System.out.println("Not Saved");
                else {
                    Deck currentDeck = decks.get(currentDeckID);

                    //Check new Deckname
                    String newDeckName = titleField.getText();
                    if(FormatData.isBlankString(newDeckName))
                        System.out.println("Deckname cannot be blank");
                    currentDeck.setDeckName(newDeckName);

                    /**
                     * Check if old tags are used as new tags
                     * if no, deletion of DeckTagBridge where the old tagID ist stored together with the deckID
                     * Check if new tags are already used as tags
                     * if yes, creating a new entry in table Tag and in DeckTag
                     * with corresponding tagID and deckID
                     * already used tags remain in both tables
                     */
                    List<String> oldTags = new ArrayList<>(currentDeck.getTags().values());
                    String[] newTags = FormatData.formatHashtags(tagField.getText());

                    for(String old : oldTags) {
                        if(!Arrays.asList(newTags).contains(old)) {
                            int tagID = DatabaseAPI.checkTag(old);
                            DatabaseAPI.deleteDeckTagBridge(currentDeck.getId(), tagID);
                        }
                    }
                    for(String newTag : newTags) {
                        if(!FormatData.isBlankString(newTag)) {
                            int tagID = DatabaseAPI.checkTag(newTag);
                            if(tagID == 0) {
                                int newTagID = DatabaseAPI.storeTag(newTag);
                                DatabaseAPI.createDeckTagBridge(currentDeck.getId(), newTagID);
                            }
                        }
                    }
                    user.editDeck(currentDeck);

                    titleField.setEditable(false);
                    tagField.setEditable(false);
                    editPanel.remove(saveBtn);
                    editPanel.remove(cnclBtn);
                    editPanel.add(editBtn);
                    editPanel.add(deleteBtn);
                    editPanel.setComponentZOrder(editBtn, 0);
                    editPanel.setComponentZOrder(deleteBtn, 1);
                    editPanel.repaint();
                    editPanel.revalidate();
                    repaintDeckCards();
                    HomeView.repaintHomeView();
                }

            }
        };
    }

    private void initEditFields() {
        titleField = new TextField((editWidth - 310) / 2, 100, "");
        titleField.setFont(MasterGUI.basicFont);
        MasterGUI.placeFieldLabel(titleField, "Deckname", editPanel, MasterGUI.white);
        editPanel.add(titleField);


        tagField = new JTextArea();
        tagField.setBackground(MasterGUI.light);
        tagField.setFont(MasterGUI.basicFont);
        tagField.setForeground(MasterGUI.black);
        tagField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        tagField.setBounds((editWidth - 310) / 2, 180, titleField.getWidth(), 110);
        tagField.setLineWrap(true);
        tagField.setEditable(true);
        MasterGUI.placeFieldLabel(tagField, "Tags", editPanel, MasterGUI.white);
        editPanel.add(tagField);
    }


    private void repaintDeckCards() {
        deckPanel.removeAll();
        //user.updateDeckList();
        decks = user.getDecks();
        int margin = 0;
        for(int i = 0; i < decks.size(); i++) {
            drawDeckCard(new Point(30,50 + margin), i,  deckPanel);
            margin = margin + 120;
        }
        deckPanel.add(addBtn);
        deckPanel.add(addLabel);
        deckPanel.repaint();
        deckPanel.revalidate();
    }

    private JScrollPane makeScroller(Panel panel){
        scroller = new JScrollPane(panel);
        scroller.setBounds(-20,0,((getWidth()+1)/2)+20, getHeight());
        scroller.getVerticalScrollBar().setUnitIncrement(10);
        scroller.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        //scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        //scroller.setVisible(false);

        return scroller;
    }
}
