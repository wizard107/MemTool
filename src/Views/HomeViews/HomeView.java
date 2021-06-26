package Views.HomeViews;

import java.awt.Color;
import java.awt.Dimension;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Model.Deck;
import Model.User;
import Views.MainGUI;
import Views.MasterGUI;
import Views.Components.Button;
import Views.Components.Label;
import Views.Components.Panel;

public class HomeView extends Panel{
    private static JFrame frame;
    private static Panel superPanel;
    private static JPanel subPanel;
    private static JScrollPane scroller;
    private ArrayList<Deck> decks;
    private User user;
    public HomeView(JFrame frame, User user){
        super(frame);
        this.user = user;
        //this.decks = user.getDecks();
        this.decks = user.getDecks();
        HomeView.frame = frame;
        superPanel = new Panel();
        subPanel = new Panel();
        //superPanel.setBounds(0,0, frame.getWidth(), frame.getHeight()*2+50);
        superPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()*2+50));
        superPanel.setBackground(MasterGUI.babyblue);
        Label x = new Label(250, 1200, "WELCOME BACK", 1, MasterGUI.purple);
        superPanel.add(x);
        /*Deck[] deck = new Deck[2];
        deck[0] = new Deck();
        deck[1] = new Deck();
        String[] test = new String[3];
        *///for(int i=0;i<decks.size();i++){test[i] = "#durability";} //limit string in size because it becomes too long otherwise
        drawDecks(superPanel);
        scroller = makeScroller(superPanel);
        add(scroller);
        frame.repaint();
    }
    private JScrollPane makeScroller(Panel panel){
        scroller = new JScrollPane(panel);
        scroller.setBounds(0,0,getWidth()+1,getHeight());
        scroller.getVerticalScrollBar().setUnitIncrement(10);
        scroller.setBorder(BorderFactory.createEmptyBorder());

        return scroller;
    }

    private void drawDecks(Panel panel){
        Point point = new Point(250 , 50);
        int deckSpace = 0;
        for(Deck deck: decks){
            Panel card = new Panel();
            card.setBounds(point.x, point.y + deckSpace, 925, 100);
            card.setBackground(MasterGUI.white);
            card.setBorder(BorderFactory.createLineBorder(Color.black));
            Label title = new Label(point.x + 60, point.y +8 + deckSpace, deck.getDeckName(), MasterGUI.black, 24f);
            Label due = new Label(point.x + 530, point.y +22 + deckSpace, "DUE", MasterGUI.green, 20f);
            Label failed = new Label(point.x + 575, point.y +22 + deckSpace, "FAILED", MasterGUI.red, 20f);
            Label neu = new Label(point.x + 640, point.y +22 + deckSpace, "NEW", MasterGUI.blue, 20f);
            Label due0 = new Label(point.x + 530, point.y +45 + deckSpace, String.valueOf(deck.calcDue()), MasterGUI.green, 20f);
            Label failed0 = new Label(point.x + 575, point.y +45 + deckSpace, String.valueOf(deck.calcAgain()), MasterGUI.red, 20f);
            Label neu0 = new Label(point.x + 640, point.y +45 + deckSpace, String.valueOf(deck.calcNew()),MasterGUI.blue, 20f);
            //Map<Integer,String> deckTags  = deck.getTags();
            List<String> deckTags = new ArrayList<>(deck.getTags().values());
            int i=0;
            for(String deckTag: deckTags){
                Label tag = new Label(point.x + 60 + i++*125, point.y + 70 + deckSpace, deckTag, MasterGUI.black, 22f);
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
            learnBtn.addActionListener(e -> {
                Panel learnMode = new LearnView(frame, deck,user);
                MainGUI.switchPanel(learnMode);
            });
            viewBtn.addActionListener(e -> {
                Panel viewMode = new CardView(frame, deck,user);
                MainGUI.switchPanel(viewMode);
            });
            Panel icon = new Panel();
            icon.setBounds(point.x + 5, point.y + 10 + deckSpace, 45, 45);
            icon.setBackground(MasterGUI.purple);
            panel.add(icon);
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

    
}
