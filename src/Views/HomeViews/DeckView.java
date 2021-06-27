package Views.HomeViews;

import Model.Deck;
import Model.User;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


import Views.Components.Panel;
import Views.Components.Label;
import Views.Components.Button;
import Views.Components.TextField;
import Views.MasterGUI;

public class DeckView extends Panel{
    private static JFrame frame;
    private static JScrollPane scroller;
    private static Panel deckPanel;
    private static Panel editPanel;

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
        deckPanel.add(new Label(0, 0 ,"OBEN LINKS", MasterGUI.blue, 20f));





        int selectMargin = 0;
        int margin = 0;
        //setBackground(MasterGUI.babyblue);
        Point point = new Point(250,50);

        for(Deck deck : decks) {
            drawDeckCard(new Point(30,50 + margin), deck,  deckPanel);
            margin = margin + 120;
            System.out.println(deck.getDeckName());
        }



        initEditFields();
        initEditButtons();

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
            List<String> deckTags = new ArrayList<>(deck.getTags().values());
            tagField.setText("");
            for(String tag : deckTags) {
                tagField.append("#" + tag);
            }
        });

        Label title = new Label(60, 8, deck.getDeckName(), MasterGUI.black, 24f);



        Button edtBtn = new Button(350 , 8 ,  "EDIT");


        Button selectBtn = new Button(point.x + 475 , point.y ,  "SELECT");


        Panel icon = new Panel();
        icon.setBounds( 5, 10 , 45, 45);
        icon.setBackground(MasterGUI.purple);

        deckCard.add(edtBtn);
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

        editPanel.add(editBtn);
        editPanel.add(deleteBtn);

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
        tagField.setEditable(false);
        MasterGUI.placeFieldLabel(tagField, "Tags", editPanel);
        editPanel.add(tagField);
    }

    public static void drawTagSection(Deck deck) {

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
