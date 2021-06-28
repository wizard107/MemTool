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

    private static Button addBtn;
    private static Button createBtn;
    private static Button deleteBtn;
    private static Button editBtn;
    private static Button saveBtn;
    private static Button cnclBtn;
    private static TextField titleField;
    private static JTextArea tagField;
    //private static List<TextField> tagFields;


    private int width;
    private int height;
    private int editWidth;

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

        editPanel.add(new Label(0, 0 ,"OBEN LINKS 2", MasterGUI.blue, 20f));

        deckPanel.setPreferredSize(new Dimension(frame.getWidth()/2 , frame.getHeight()*2+50));
        //deckPanel.setBounds(0,0, frame.getWidth()/2, frame.getHeight()*2+50);
        deckPanel.setBackground(MasterGUI.babyblue);
        //deckPanel.add(new Label(0, 0 ,"OBEN LINKS", MasterGUI.blue, 20f));





        int selectMargin = 0;
        int margin = 0;
        //setBackground(MasterGUI.babyblue);
        Point point = new Point(250,50);

        initEditFields();
        initEditButtons();
        initAddBtn();
        initCreateDeckBtn();

        for(Deck deck : decks) {
            drawDeckCard(new Point(30,50 + margin), deck,  deckPanel);
            margin = margin + 120;
            //System.out.println(deck.getDeckName());
        }




        //initEditFields();
        //initEditButtons();
        //initAddBtn();
        //initCreateDeckBtn();

        //deckPanel.add(editPanel);
        scroller = makeScroller(deckPanel);
        add(scroller);
        //this.add(deckPanel);
        //this.add(sp);
        this.add(editPanel);
        frame.repaint();

    }

    public void drawDeckCard(Point point, Deck deck, Panel panel) {
        Panel deckCard = new Panel();
        deckCard.setBounds(point.x, point.y, 450, 100);
        deckCard.setBackground(MasterGUI.white);
        deckCard.setBorder(BorderFactory.createLineBorder(Color.black));
        deckCard.setOpaque(true);

        Button cardBtn = new Button(0,0,"");
        cardBtn.setSize(deckCard.getWidth(), deckCard.getHeight());
        cardBtn.setFocusPainted(false);
        cardBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) { deckCard.setBackground(MasterGUI.babyred); }
            @Override
            public void mouseExited(MouseEvent e) { deckCard.setBackground(MasterGUI.white); }
        });
        cardBtn.addActionListener(e -> {
            titleField.setText(deck.getDeckName());
            titleField.setEditable(false);
            List<String> deckTags = new ArrayList<>(deck.getTags().values());
            tagField.setText("");
            for(String tag : deckTags) {
                tagField.append("#" + tag + " ");
            }
            tagField.setEditable(false);
            editPanel.remove(cnclBtn);
            editPanel.remove(createBtn);
            editPanel.add(editBtn);
            editPanel.add(deleteBtn);
            editPanel.repaint();
            editPanel.revalidate();
        });

        Label title = new Label(60, 8, deck.getDeckName(), MasterGUI.black, 24f);


        Button viewBtn = new Button(325 , 25 ,  "VIEW", MasterGUI.yellow, 110, 40);
        viewBtn.setFont(MasterGUI.poppinsFontBig);
        viewBtn.setForeground(MasterGUI.black);
        viewBtn.setContentAreaFilled(true);
        viewBtn.setFocusPainted(false);
        viewBtn.addActionListener(e -> {
            Panel viewMode = new CardView(frame, deck,user);
            MainGUI.switchPanel(viewMode);
        });


        Button selectBtn = new Button(point.x + 475 , point.y ,  "SELECT");

        deleteBtn = new Button((editWidth - 310) / 2, 450, "DELETE DECK", MasterGUI.red, titleField.getWidth(), titleField.getHeight());
        ActionListener deleteAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.deleteDeck(deck);
                user.updateDeckList();
                toCreateMode();
                repaintDeckCards();
                HomeView.repaintHomeView();
            }
        };
        deleteBtn.addActionListener(e -> MainGUI.confirmDialog(deleteAction, "Are sure that you want to delete?"));

        Panel icon = new Panel();
        icon.setBounds( 5, 10 , 45, 45);
        icon.setBackground(MasterGUI.purple);

        deckCard.add(viewBtn);
        //deckCard.add(deleteBtn);
        deckCard.add(cardBtn);
        //deckCard.add(deckNameField);
        deckCard.add(title);
        deckCard.add(icon);
        panel.add(selectBtn);
        panel.add(deckCard);

        deckCard.repaint();
        panel.repaint();

        deckCard.revalidate();
        panel.revalidate();
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
    }

    private void toCreateMode() {
        titleField.setToEditMode();
        tagField.setEditable(true);
        editPanel.remove(editBtn);
        editPanel.remove(deleteBtn);
        editPanel.remove(cnclBtn);
        editPanel.add(createBtn);
        //editPanel.setComponentZOrder(createBtn, 0);
        editPanel.repaint();
        editPanel.revalidate();
    }

    private void initCreateDeckBtn() {
        createBtn = new Button((editWidth - 310) / 2, 400, "CREATE NEW DECK", MasterGUI.green, titleField.getWidth(), titleField.getHeight());
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
                //System.out.println(tag);
            }
        }


    }

    private void initEditButtons() {
        editBtn = new Button((editWidth - 310) / 2, 400, "EDIT DECK", MasterGUI.yellow, titleField.getWidth(), titleField.getHeight());
        editBtn.setContentAreaFilled(true);
        editBtn.setFocusPainted(false);
        editBtn.addActionListener(e -> {
            titleField.setToEditMode();
            tagField.setEditable(true);
            editPanel.remove(editBtn);
            editPanel.remove(deleteBtn);
            editPanel.add(cnclBtn);
            editPanel.setComponentZOrder(cnclBtn, 0);
            editPanel.repaint();
            editPanel.revalidate();
        });

        deleteBtn = new Button((editWidth - 310) / 2, 450, "DELETE DECK", MasterGUI.red, titleField.getWidth(), titleField.getHeight());
        //deleteBtn.addActionListener();


        cnclBtn = new Button((editWidth - 310) / 2, 450, "CANCEL", MasterGUI.yellow, titleField.getWidth(), titleField.getHeight());
        cnclBtn.addActionListener(e -> {
            titleField.setToEditMode();
            tagField.setEditable(false);
            editPanel.remove(cnclBtn);
            editPanel.add(editBtn);
            editPanel.add(deleteBtn);
            editPanel.setComponentZOrder(editBtn, 0);
            editPanel.setComponentZOrder(deleteBtn, 1);
            editPanel.repaint();
            editPanel.revalidate();
        });

        //editPanel.add(editBtn);
        //editPanel.add(deleteBtn);

    }


    private void initEditFields() {
        titleField = new TextField((editWidth - 310) / 2, 100, "HELLO TEXTFIELD");
        titleField.setFont(MasterGUI.basicFont);
        titleField.setToStaticMode();
        MasterGUI.placeFieldLabel(titleField, "Deckname", editPanel);
        editPanel.add(titleField);


        tagField = new JTextArea();
        tagField.setBackground(MasterGUI.light);
        tagField.setFont(MasterGUI.basicFont);
        tagField.setForeground(MasterGUI.black);
        tagField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        tagField.setBounds((editWidth - 310) / 2, 180, titleField.getWidth(), 110);
        tagField.setLineWrap(true);
        tagField.setEditable(true);
        MasterGUI.placeFieldLabel(tagField, "Tags", editPanel);
        editPanel.add(tagField);
    }


    private void repaintDeckCards() {
        deckPanel.removeAll();
        //user.updateDeckList();
        decks = user.getDecks();
        int margin = 0;
        for(Deck deck : decks) {
            drawDeckCard(new Point(30,50 + margin), deck,  deckPanel);
            margin = margin + 120;
        }
        deckPanel.add(addBtn);
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
