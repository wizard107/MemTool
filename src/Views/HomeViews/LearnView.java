package Views.HomeViews;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;

import Controller.DatabaseAPI;

import java.awt.Point;

import Model.Card;
import Model.Deck;
import Model.User;
import Views.MasterGUI;
import Views.Components.Button;
import Views.Components.Label;
import Views.Components.Panel;
import Views.Components.TextPane;

public class LearnView extends Panel{
    private JFrame frame;
    private Deck deck;
    private Point point = new Point(50 , 50);
    private Panel learnPanel;
    private Panel answerPanel;
    private Panel questionPanel;
    private int count = 0;
    private User user;
    private int cardsLeftNumber = 0;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private PriorityQueue<Card> queue;
    private Label question;
    private Label answer;
    private Button revealBtn;
    private Button easy;
    private Button medium;
    private Button difficult;
    private Label title;
    private Label cardsLeft;
    private Label due0;
    private Label failed0;
    private Label neu0;
    private TextPane questionPane;
    private TextPane answerPane;

    public LearnView(JFrame frame, Deck deck, User user){
        super(frame);
        this.frame = frame;
        this.deck  = deck;
        this.user = user;
        Comparator<Card> dueDateComparator = new Comparator<Card>(){
            @Override
            public int compare(Card c1, Card c2){
                return (int)c1.getDueTime() - (int)c2.getDueTime();
            }
        };
        queue = new PriorityQueue<>(dueDateComparator);
        cards = deck.getCards();
        for(int i=0;i<cards.size();i++){
            Card card = cards.get(i);
            if((card.getDueDate().equals(LocalDate.now()))||(card.getDueDate().isBefore(LocalDate.now()))) queue.add(card);
        }
        cardsLeftNumber = queue.size();
        questionPane = new TextPane(point.x, point.y, 500, 450);
        answerPane = new TextPane(point.x + 575, point.y, 500, 450);

        learnPanel = new Panel();
        learnPanel.setBounds(0,0, frame.getWidth(), frame.getHeight());
        learnPanel.setBackground(MasterGUI.black);
        learnPanel.add(questionPane);
        
        
        createButtons();
        createLearnView();
        add(learnPanel);
        createQA();           
    }
    private void createButtons(){
        revealBtn = new Button(point.x , point.y + 475 , "Reveal Answer", MasterGUI.purple, 1075, 80);
        easy = new Button(point.x , point.y + 475 , "easy", MasterGUI.green, 350, 80);
        medium = new Button(point.x + 363 , point.y + 475 , "ok", MasterGUI.blue, 350, 80);
        difficult = new Button(point.x + 725 , point.y + 475 , "difficult", MasterGUI.red, 350, 80);
        learnPanel.add(revealBtn);
        learnPanel.add(easy);
        learnPanel.add(medium);
        learnPanel.add(difficult);

        MasterGUI.setComponentStyles(learnPanel, "dark");
        learnPanel.remove(easy);
        learnPanel.remove(medium);
        learnPanel.remove(difficult);
        revealBtn.setFont(MasterGUI.poppinsFontBig);
        easy.setFont(MasterGUI.poppinsFontBig);
        medium.setFont(MasterGUI.poppinsFontBig);
        difficult.setFont(MasterGUI.poppinsFontBig);
        ButtonFunctionality();
        newCard();
    }
    private void createLearnView(){
        title = new Label(point.x, point.y -30 , deck.getDeckName(), MasterGUI.white, 24f);
        cardsLeft = new Label(point.x + 825, point.y -30 , "Cards Left: " + String.valueOf(cardsLeftNumber), MasterGUI.white, 24f);


        learnPanel.add(title);
        learnPanel.add(cardsLeft);

        
        }
    private void removeBasicLearnView(){
        learnPanel.remove(title);
        learnPanel.remove(cardsLeft);

    }
    private void createQA(){

        questionPane.setText(queue.peek().getFrontText());
        answerPane.setText(queue.peek().getBackText());

    }
    private void newCard(){
        ActionListener revealCard = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                

                learnPanel.add(answerPane);
                
            }
        };
        ActionListener changeCardEasy = new ActionListener(){
            public void actionPerformed(ActionEvent e){
              
                questionPane.setText("");
                learnPanel.remove(answerPane);
                answerPane.setText("");

                try{
                    Card card = queue.poll();
                    int num = card.getDueTime();
                    card.setDueTime(num+3);
                    if((card.getDueTime()<10)&&(card.getWasForgotten()||card.getIsNew())){
                        queue.add(card);
                    }  
                    else{
                        card.setDueTime(0);
                        if(card.getIsNew()||card.getWasForgotten())card.setDueDate(LocalDate.now().plusDays(1));
                        else{
                            card.setCorrect(card.getCorrect()+1);
                            card.setDueDate(LocalDate.now().plusDays(calcNextDueDate(card.getCorrect(), true)));
                        }
                        cardsLeftNumber--;
                        card.setWasForgotten(false);
                        card.setIsNew(false);

                    }
                    cards.set(card.getCardPosition(), card);
                    deck.setCardDeck(cards);
                    user.setMainDeck(deck.getDeckPosition(), deck);
                    DatabaseAPI.editCard(card);
                    HomeView.repaintHomeView();
                    removeBasicLearnView();
                    createLearnView();
                    learnPanel.repaint();
                    System.out.println("easy");
                    createQA();
                }catch(NullPointerException np){
                    System.out.println("easy card=null");
                    System.out.println("no cards left"); 
                    
                    questionPane.setText("No Cards Left");
                    answerPane.setText("Still no Cards left");
                    for(ActionListener a : easy.getActionListeners()){
                        easy.removeActionListener(a);
                    }
                    for(ActionListener a : medium.getActionListeners()){
                        easy.removeActionListener(a);
                    }
                    for(ActionListener a : difficult.getActionListeners()){
                        easy.removeActionListener(a);
                    }
                }
                
            }
        };
        ActionListener changeCardMedium = new ActionListener(){
            public void actionPerformed(ActionEvent e){
    
                questionPane.setText("");
                learnPanel.remove(answerPane);
                answerPane.setText("");
                try{
                Card card = queue.poll();
                int num = card.getDueTime();
                    card.setDueTime(num+2);
                    if((card.getDueTime()<10)&&(card.getWasForgotten()||card.getIsNew())){
                        queue.add(card);
                    }  
                    else{
                        card.setDueTime(0);
                        if(card.getIsNew()||card.getWasForgotten())card.setDueDate(LocalDate.now().plusDays(1));
                        else{
                            card.setCorrect(card.getCorrect()+1);
                            card.setDueDate(LocalDate.now().plusDays(calcNextDueDate(card.getCorrect(), false)));
                        }
                        cardsLeftNumber--;
                        card.setWasForgotten(false);
                        card.setIsNew(false);
                        
                    }
                    
                    cards.set(card.getCardPosition(), card);
                    deck.setCardDeck(cards);
                    user.setMainDeck(deck.getDeckPosition(), deck);
                    DatabaseAPI.editCard(card);
                    HomeView.repaintHomeView();
                    removeBasicLearnView();
                    createLearnView();
                    learnPanel.repaint();
                    System.out.println("medium");
                    createQA();
                }catch(NullPointerException npe){
                    System.out.println("null");
                    System.out.println("no cards left"); 
                   
                    questionPane.setText("No Cards Left");
                    answerPane.setText("Still no Cards left");
                    for(ActionListener a : easy.getActionListeners()){
                        medium.removeActionListener(a);
                    }
                    for(ActionListener a : medium.getActionListeners()){
                        medium.removeActionListener(a);
                    }
                    for(ActionListener a : difficult.getActionListeners()){
                        medium.removeActionListener(a);
                    }
                }
                
            }
        };
        ActionListener changeCardDifficult = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                questionPane.setText("");
                learnPanel.remove(answerPane);
                answerPane.setText("");
                try{
                    Card card = queue.poll();
                    card.setDueTime(0);
                    queue.add(card);
                    if((!card.getIsNew())&&(!card.getWasForgotten()))card.setWasForgotten(true);
                    cards.set(card.getCardPosition(), card);
                    deck.setCardDeck(cards);
                    user.setMainDeck(deck.getDeckPosition(), deck);
                    DatabaseAPI.editCard(card);
                    HomeView.repaintHomeView();
                    removeBasicLearnView();
                    createLearnView();
                    learnPanel.repaint();
                    System.out.println("hard");
                    createQA();
                }catch(NullPointerException np){
                    System.out.println("no cards left"); 
                    
                    questionPane.setText("No Cards Left");
                    answerPane.setText("Still no Cards left");
                    for(ActionListener a : easy.getActionListeners()){
                        difficult.removeActionListener(a);
                    }
                    for(ActionListener a : medium.getActionListeners()){
                        difficult.removeActionListener(a);
                    }
                    for(ActionListener a : difficult.getActionListeners()){
                        difficult.removeActionListener(a);
                    }
                }
                
            }
        };
        revealBtn.addActionListener(revealCard);
        easy.addActionListener(changeCardEasy);
        medium.addActionListener(changeCardMedium);
        difficult.addActionListener(changeCardDifficult);

    }
    private void ButtonFunctionality(){
        ActionListener changeButtons = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                learnPanel.add(easy);
                learnPanel.add(medium);
                learnPanel.add(difficult);
                learnPanel.remove(revealBtn);
                learnPanel.repaint();
                
            }
        };
        ActionListener cardEasy = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                learnPanel.remove(easy);
                learnPanel.remove(medium);
                learnPanel.remove(difficult);
                learnPanel.add(revealBtn);
                learnPanel.repaint();
                
            }
        };
        ActionListener cardMedium = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                learnPanel.remove(easy);
                learnPanel.remove(medium);
                learnPanel.remove(difficult);
                learnPanel.add(revealBtn);
                learnPanel.repaint();
                
            }
        };
        ActionListener cardDifficult = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                learnPanel.remove(easy);
                learnPanel.remove(medium);
                learnPanel.remove(difficult);
                learnPanel.add(revealBtn);
                learnPanel.repaint();
                
            }
        };
        revealBtn.addActionListener(changeButtons);
        easy.addActionListener(cardEasy);
        medium.addActionListener(cardMedium);
        difficult.addActionListener(cardDifficult);

    }
    public int calcNextDueDate(int correct, boolean isEasy){
        int nextDueDate = 1;
        if(isEasy)nextDueDate = (int) Math.pow(2,correct+1);
        else nextDueDate = (int)Math.pow(2,correct);
        return nextDueDate;   
    }
}
