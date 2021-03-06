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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

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
    private static ArrayList<Deck> decks;
    private static User user;
    public HomeView(JFrame frame, User user){
        super(frame);
        this.user = user;
        this.decks = user.getDecks();
        HomeView.frame = frame;
        superPanel = new Panel();
        subPanel = new Panel();
        superPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()*2+50));
        superPanel.setBackground(MasterGUI.babyblue);
        Label x = new Label(250, 1200, "WELCOME BACK", 1, MasterGUI.purple);
        superPanel.add(x);
   
        drawDecks(superPanel);
        drawProfile();
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
    public static void repaintHomeView(){
        superPanel.removeAll();
        decks = user.getDecks();
        drawDecks(superPanel);
        drawProfile();
        frame.repaint();
    }

    public static void drawProfile(){
        Label welcome = new Label(20,10, "Welcome Back", MasterGUI.black, 30f);
        Panel profileBox = new Panel();
        profileBox.setBounds(20, 50, 210, 220);
        profileBox.setBackground(MasterGUI.black_gray);
        Label username = new Label(5,20, user.getUsername(), MasterGUI.black,24f);
        username.setForeground(MasterGUI.white);
        Panel deckbg = new Panel();
        deckbg.setBounds(25, 110, 200, 50);
        deckbg.setBackground(MasterGUI.yellow);
        Panel card1bg = new Panel();
        card1bg.setBounds(25, 175, 95, 50);
        card1bg.setBackground(MasterGUI.yellow);
        Panel card2bg = new Panel();
        card2bg.setBounds(130, 175, 95, 50);
        card2bg.setBackground(MasterGUI.yellow);
        Label decksTotal = new Label(110,115, String.valueOf(user.getDecks().size()), MasterGUI.black,24f);
        decksTotal.setForeground(MasterGUI.black);
        Label decksTotalTxt = new Label(75,135, "Decks Total", MasterGUI.black,20f);
        decksTotalTxt.setFont(MasterGUI.poppinsFontBig.deriveFont(15f));
        int test[] = user.calcCardsLearned();
        Label cardsLearned = new Label(65,180, String.valueOf(test[0]), MasterGUI.black,24f);
        cardsLearned.setForeground(MasterGUI.black);
        Label cardsLearnedTxt = new Label(35,200, "Cards Learned", MasterGUI.black,15f);
        cardsLearnedTxt.setFont(MasterGUI.poppinsFontBig.deriveFont(10f));
        Label cardsNew = new Label(170,180, String.valueOf(test[1]), MasterGUI.black,24f);
        cardsNew.setForeground(MasterGUI.black);
        Label cardsNewTxt = new Label(132,200, "Cards yet to learn", MasterGUI.black,15f);
        cardsNewTxt.setFont(MasterGUI.poppinsFontBig.deriveFont(10f));
        JLabel thinkImage = new JLabel(MasterGUI.thinkPNG);
        thinkImage.setBounds(-40, 200, 500, 500);
        superPanel.add(decksTotalTxt);
        superPanel.add(decksTotal);
        superPanel.add(cardsLearnedTxt);
        superPanel.add(cardsLearned);
        superPanel.add(cardsNew);
        superPanel.add(cardsNewTxt);
        superPanel.add(card1bg);
        superPanel.add(card2bg);
        profileBox.add(username);
        superPanel.add(welcome);
        superPanel.add(deckbg);
        superPanel.add(profileBox);
        superPanel.add(thinkImage);
    }

    private static void drawDecks(Panel panel){
        Point point = new Point(250 , 50);
        int deckSpace = 0;
        for(Deck deck: decks){
            Panel card = new Panel();
            card.setBounds(point.x, point.y + deckSpace, 925, 100);
            card.setBackground(MasterGUI.white);
            card.setBorder(BorderFactory.createLineBorder(Color.black));
            Label title = new Label(point.x + 5, point.y +8 + deckSpace, deck.getDeckName(), MasterGUI.black, 24f);
            Label due = new Label(point.x + 530, point.y +22 + deckSpace, "DUE", MasterGUI.green, 20f);
            Label failed = new Label(point.x + 575, point.y +22 + deckSpace, "FAILED", MasterGUI.red, 20f);
            Label neu = new Label(point.x + 640, point.y +22 + deckSpace, "NEW", MasterGUI.blue, 20f);
            Label due0 = new Label(point.x + 530, point.y +45 + deckSpace, String.valueOf(deck.calcDue()), MasterGUI.green, 20f);
            Label failed0 = new Label(point.x + 575, point.y +45 + deckSpace, String.valueOf(deck.calcAgain()), MasterGUI.red, 20f);
            Label neu0 = new Label(point.x + 640, point.y +45 + deckSpace, String.valueOf(deck.calcNew()),MasterGUI.blue, 20f);
            List<String> deckTags = new ArrayList<>(deck.getTags().values());
            int i=0;
            for(String deckTag: deckTags){
                Label tag = new Label(point.x + 5 + i++*125, point.y + 70 + deckSpace, deckTag, MasterGUI.black, 22f);
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
                try{
                Panel learnMode = new LearnView(frame, deck,user);
                MainGUI.switchPanel(learnMode);
                }catch(NullPointerException np){
                    System.out.println("No Cards Left to Learn");
                }
                
            });
            viewBtn.addActionListener(e -> {
                Panel viewMode = new CardView(frame, deck,user);
                MainGUI.switchPanel(viewMode);
            });
  
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
