package Views.HomeViews;

import Model.Deck;
import Model.User;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


import Views.Components.Panel;
import Views.Components.Label;
import Views.MasterGUI;

public class DeckView extends Panel{
    private static JFrame frame;
    private static Panel deckPanel;

    private ArrayList<Deck> decks;
    private User user;

    public DeckView(JFrame frame, User user){
        super(frame);
        this.frame = frame;
        this.deckPanel = this;

        this.user = user;
        this.decks = user.getDecks();

        deckPanel.add(new Label(250, 50, "HELLO", MasterGUI.blue, 20f));

        setBackground(Color.GREEN);
        for(Deck deck : decks) {
            int margin = 250;
            drawDeckCard(new Point(250,50 + margin), deckPanel);
            margin += 120;
        }



        //setBackground(Color.GREEN);
    }

    public void drawDeckCard(Point point, Panel panel) {
       // Point p = new Point(250 , 50);
        Panel deckCard = new Panel();
        int margin = 0;
        deckCard.setRounded(true);
        deckCard.setBounds(point.x, point.y + margin, 925, 100);
        deckCard.setBackground(MasterGUI.white);
        deckCard.setBorder(BorderFactory.createLineBorder(Color.black));

        panel.add(deckCard);
        setVisible(true);
    }
}
