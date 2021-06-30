package Views.HomeViews;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import Controller.DatabaseAPI;
import Model.Deck;
import Views.Components.Button;
import Views.Components.Label;
import Views.Components.Panel;
import Views.Components.TextField;
import Views.MainGUI;
import Views.MasterGUI;

public class SearchView extends Panel {
    private static JFrame frame;
    private static Panel superPanel;
    private static JScrollPane scroller;

    private static TextField searchField;
    private static Button searchBtn;

    private static ArrayList<Deck> publicDecks;

    private int width; //1200
    private int height;//700

    public SearchView(JFrame frame) {
        super(frame);
        this.frame = frame;

        this.width = frame.getWidth();
        this.height = frame.getHeight();

        System.out.println(width);
        System.out.println(height);

        //this.publicDecks = DatabaseAPI.getAllPublicDecks();

        superPanel = new Panel();
        superPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()*2+50));
        superPanel.setBackground(MasterGUI.babyblue);

        Label x = new Label(0, 0, "WELCOME BACK", 1, MasterGUI.purple);
        superPanel.add(x);
        drawDeckCards(superPanel);

        initSearchField();

        scroller = makeScroller(superPanel);
        add(scroller);

        frame.repaint();


        //setBackground(Color.RED);
    }

    private void initSearchField() {
        searchField = new TextField(445, 10, "Search Deck");
        superPanel.add(searchField);
    }

    private static void drawDeckCards(Panel panel) {
        Point point = new Point(250, 50);
        int deckSpace = 0;
        for (Deck deck : publicDecks) {
            Panel card = new Panel();
            card.setBounds(point.x, point.y + deckSpace, 925, 100);
            card.setBackground(MasterGUI.white);
            card.setBorder(BorderFactory.createLineBorder(Color.black));
            Label title = new Label(point.x + 5, point.y + 8 + deckSpace, deck.getDeckName(), MasterGUI.black, 24f);
            Label due = new Label(point.x + 530, point.y + 22 + deckSpace, "DUE", MasterGUI.green, 20f);
            Label failed = new Label(point.x + 575, point.y + 22 + deckSpace, "FAILED", MasterGUI.red, 20f);
            Label neu = new Label(point.x + 640, point.y + 22 + deckSpace, "NEW", MasterGUI.blue, 20f);
            Label due0 = new Label(point.x + 530, point.y + 45 + deckSpace, String.valueOf(deck.calcDue()), MasterGUI.green, 20f);
            Label failed0 = new Label(point.x + 575, point.y + 45 + deckSpace, String.valueOf(deck.calcAgain()), MasterGUI.red, 20f);
            Label neu0 = new Label(point.x + 640, point.y + 45 + deckSpace, String.valueOf(deck.calcNew()), MasterGUI.blue, 20f);
            //Map<Integer,String> deckTags  = deck.getTags();
            List<String> deckTags = new ArrayList<>(deck.getTags().values());
            int i = 0;
            for (String deckTag : deckTags) {
                Label tag = new Label(point.x + 5 + i++ * 125, point.y + 70 + deckSpace, deckTag, MasterGUI.black, 22f);
                panel.add(tag);
            }
            Button viewBtn = new Button(point.x + 700, point.y + 25 + deckSpace, "VIEW", MasterGUI.yellow, 110, 40);
            viewBtn.setFont(MasterGUI.poppinsFontBig);
            viewBtn.setForeground(MasterGUI.black);
            viewBtn.setContentAreaFilled(true);
            viewBtn.setFocusPainted(false);
            Button learnBtn = new Button(point.x + 810, point.y + 25 + deckSpace, "LEARN", MasterGUI.purple, 110, 40);
            learnBtn.setFont(MasterGUI.poppinsFontBig);
            learnBtn.setContentAreaFilled(true);
            learnBtn.setForeground(MasterGUI.black);
            learnBtn.setFocusPainted(false);
            /*
            learnBtn.addActionListener(e -> {
                try {
                    Panel learnMode = new LearnView(frame, deck, user);
                    MainGUI.switchPanel(learnMode);
                } catch (NullPointerException np) {
                    System.out.println("No Cards Left to Learn");
                }

            });

             *//*
            viewBtn.addActionListener(e -> {
                Panel viewMode = new CardView(frame, deck, user);
                MainGUI.switchPanel(viewMode);
            });*/
            //Panel icon = new Panel();
            //icon.setBounds(point.x + 5, point.y + 10 + deckSpace, 45, 45);
            //icon.setBackground(MasterGUI.purple);
            //panel.add(icon);
            panel.add(title);
            panel.add(viewBtn);
            panel.add(learnBtn);
            panel.add(due);
            panel.add(neu);
            panel.add(failed);
            panel.add(due0);
            panel.add(failed0);
            panel.add(neu0);

            panel.add(card);
            deckSpace += 120;
        }
    }

    private JScrollPane makeScroller(Panel panel){
        scroller = new JScrollPane(panel);
        scroller.setBounds(0,0,getWidth()+1,getHeight());
        scroller.getVerticalScrollBar().setUnitIncrement(10);
        scroller.setBorder(BorderFactory.createEmptyBorder());

        return scroller;
    }

}
