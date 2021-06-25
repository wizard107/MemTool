package Views.HomeViews;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Point;

import Model.Card;
import Model.Deck;
import Views.MasterGUI;
import Views.Components.Button;
import Views.Components.Label;
import Views.Components.Panel;

public class LearnView extends Panel{
    private JFrame frame;
    private Deck deck;
    private Point point = new Point(50 , 50);
    private Panel learnPanel;
    private Panel answerPanel;
    private Panel questionPanel;
    private int count = 0;
    private int cardsLeftNumber = 0;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private PriorityQueue<Card> queue;
    private Label question;
    private Label answer;
    private Button revealBtn;
    private Button easy;
    private Button medium;
    private Button difficult;
    //ADD duedate to know which are questioned this day and use duetime as priority measurement to see which has the earliest turn.
    //everyone starts with duetime 0
    //if a card was learned but forgotten isForgotten becomes 1 it will be treated as a new card except in name
    //a card that was learned and was guessed right becomes duetime 10 it will be removed from queue
    //duetime will be reset to 0
    //we differentiate between guessed right and ok or easy -> different duedate
    //new cards if guessed right need to get to duetime 10
    //if answered with difficult = 0
    //if answered with ok + 2
    //if answered with easy +3
    //isnew will become false after reaching duetime 10
    public LearnView(JFrame frame, Deck deck){
        super(frame);
        this.frame = frame;
        this.deck  = deck;
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
            //if(card.getDueTime()<=1)queue.add(card); this needs to compare dateentrys with today and earlier dates
            card.setDueTime(0);
            queue.add(card);

        }
        cardsLeftNumber = queue.size();
        questionPanel = new Panel();
        answerPanel = new Panel();
        questionPanel.setBounds(point.x, point.y, 500, 450);
        answerPanel.setBounds(point.x + 575, point.y, 500, 450);
        learnPanel = new Panel();
        learnPanel.setBounds(0,0, frame.getWidth(), frame.getHeight());
        //learnPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()*2+50));
        learnPanel.setBackground(MasterGUI.black);
        
        /*String[] testq = new String[3];
        String[] testa = new String[3];

        testq[0] = "test1";
        testq[1] = "test2";
        testq[2] = "test3";
        testa[0] = "test1";
        testa[1] = "test2";
        testa[2] = "test3";*/
        createButtons();
        createLearnView();
        add(learnPanel);
        createQA();           
        //add(learnPanel);        //has to be a loop where createlearnview is called multipletime but with different cards, while panel is always repainted
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
        Label title = new Label(point.x, point.y -30 , deck.getDeckName(), MasterGUI.white, 24f);
        Label cardsLeft = new Label(point.x + 825, point.y -30 , String.valueOf(cardsLeftNumber), MasterGUI.white, 24f);
        Label due0 = new Label(point.x + 995, point.y -30 , String.valueOf(deck.getDue()), MasterGUI.green, 20f);
        Label failed0 = new Label(point.x + 1030, point.y -30 , String.valueOf(deck.getAgain()), MasterGUI.red, 20f);
        Label neu0 = new Label(point.x + 1065, point.y -30 , String.valueOf(deck.getNewCards()), MasterGUI.blue, 20f);

        learnPanel.add(title);
        learnPanel.add(cardsLeft);
        learnPanel.add(due0);
        learnPanel.add(failed0);
        learnPanel.add(neu0);
        
        }
    private void createQA(){
//        question = new Label(point.x, point.y+5 , "test" + String.valueOf(count), MasterGUI.black_gray, 20f);
        question = new Label(point.x, point.y+5 , queue.peek().getFrontText(), MasterGUI.black_gray, 20f);

        question.setBounds(point.x, point.y, 499, 449);
        answer = new Label(point.x + 575, point.y, queue.peek().getBackText(), MasterGUI.black_gray,20f);
        answer.setBounds(point.x+575, point.y, 499, 449);
        learnPanel.add(question);
        learnPanel.add(questionPanel);
    }
    private void newCard(){
        ActionListener revealCard = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                learnPanel.add(answer);
                learnPanel.add(answerPanel);
                
            }
        };
        ActionListener changeCardEasy = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //wenn forgotten zutrifft und noch nicht ist muss hier auf forgotten gestellt werden
                learnPanel.remove(questionPanel);
                learnPanel.remove(answerPanel);
                learnPanel.remove(question);
                learnPanel.remove(answer);
                try{
                Card card = queue.poll();
                int num = (int)card.getDueTime();
                    card.setDueTime(num+3);
                    if(card.getDueTime()<10){
                        queue.add(card);
                    }  
                    else{
                        cards.get(card.getId()-1).setDueTime(0);//hier muss eigentlich duedate eingesetzt werden.
                        cards.set(card.getId()-1, card);
                        deck.setCardDeck(cards);
                    }
                    //add forgotten and isnwer functions
                    System.out.println("easy");
                    createQA();
                }catch(NullPointerException np){
                    System.out.println("easy card=null");
                    System.out.println("no cards left"); //<<------------------------------------- ab hier switch back
                    question = new Label(point.x, point.y+5 , "No Cards Left", MasterGUI.black_gray, 20f);
                    question.setBounds(point.x, point.y, 499, 449);
                    answer = new Label(point.x + 575, point.y, "Still no Cards Left", MasterGUI.black_gray,20f);
                    answer.setBounds(point.x+575, point.y, 499, 449);
                    //learnPanel.removeAll();
                    learnPanel.add(question);
                    learnPanel.add(questionPanel);
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
                /*if(card==null){
                    System.out.println("no cards left"); //<<------------------------------------- ab hier switch back
                    question = new Label(point.x, point.y+5 , "No Cards Left", MasterGUI.black_gray, 20f);
                    question.setBounds(point.x, point.y, 499, 449);
                    answer = new Label(point.x + 575, point.y, "Still no Cards Left", MasterGUI.black_gray,20f);
                    answer.setBounds(point.x+575, point.y, 499, 449);
                    //learnPanel.removeAll();
                    learnPanel.add(question);
                    learnPanel.add(questionPanel);
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
                else{
                    int num = (int)card.getDueTime();
                    card.setDueTime(num+3);
                    if(card.getDueTime()<10){
                        queue.add(card);
                    }  
                    else{
                        cards.get(card.getId()-1).setDueTime(0);//hier muss eigentlich duedate eingesetzt werden.
                        cards.set(card.getId()-1, card);
                        deck.setCardDeck(cards);
                    }
                    //add forgotten and isnwer functions
                    System.out.println("easy");
                    createQA();
                }*/
            }
        };
        ActionListener changeCardMedium = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                learnPanel.remove(questionPanel);
                learnPanel.remove(answerPanel);
                learnPanel.remove(question);
                learnPanel.remove(answer);
                try{
                Card card = queue.poll();
                int num = (int)card.getDueTime();
                    card.setDueTime(num+2);
                    if(card.getDueTime()<10){
                        queue.add(card);
                    }  
                    else{
                        cards.get(card.getId()-1).setDueTime(10);//hier muss eigentlich duedate eingesetzt werden zusätzlich.
                        cards.set(card.getId()-1, card);
                        deck.setCardDeck(cards);
                    }
                    //add forgotten and isnwer functions
                    System.out.println("medium");
                    createQA();
                }catch(NullPointerException npe){
                    System.out.println("null");
                    System.out.println("no cards left"); //<<------------------------------------- ab hier switch back
                    question = new Label(point.x, point.y+5 , "No Cards Left", MasterGUI.black_gray, 20f);
                    question.setBounds(point.x, point.y, 499, 449);
                    answer = new Label(point.x + 575, point.y, "Still no Cards Left", MasterGUI.black_gray,20f);
                    answer.setBounds(point.x+575, point.y, 499, 449);
                    //learnPanel.removeAll();
                    learnPanel.add(question);
                    learnPanel.add(questionPanel);
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
                /*if(card==null){
                    System.out.println("no cards left"); //<<------------------------------------- ab hier switch back
                    question = new Label(point.x, point.y+5 , "No Cards Left", MasterGUI.black_gray, 20f);
                    question.setBounds(point.x, point.y, 499, 449);
                    answer = new Label(point.x + 575, point.y, "Still no Cards Left", MasterGUI.black_gray,20f);
                    answer.setBounds(point.x+575, point.y, 499, 449);
                    //learnPanel.removeAll();
                    learnPanel.add(question);
                    learnPanel.add(questionPanel);
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
                else{
                    //prüfen ob forgotten ist oder nicht, wenn forgotten dann weiter, wenn nicht dann direkt ersetzen mit neuem duedate
                    //if isforgotten||isnew
                    int num = (int)card.getDueTime();
                    card.setDueTime(num+2);
                    if(card.getDueTime()<10){
                        queue.add(card);
                    }  
                    else{
                        cards.get(card.getId()-1).setDueTime(10);//hier muss eigentlich duedate eingesetzt werden zusätzlich.
                        cards.set(card.getId()-1, card);
                        deck.setCardDeck(cards);
                    }
                    //add forgotten and isnwer functions
                    System.out.println("medium");
                    createQA();
                }*/
            }
        };
        ActionListener changeCardDifficult = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                learnPanel.remove(questionPanel);
                learnPanel.remove(answerPanel);
                learnPanel.remove(question);
                learnPanel.remove(answer);
                try{
                    Card card = queue.poll();
                    card.setDueTime(0);
                    queue.add(card);
                    System.out.println("hard");
                    createQA();
                }catch(NullPointerException np){
                    System.out.println("no cards left"); //<<------------------------------------- ab hier switch back
                    question = new Label(point.x, point.y+5 , "No Cards Left", MasterGUI.black_gray, 20f);
                    question.setBounds(point.x, point.y, 499, 449);
                    answer = new Label(point.x + 575, point.y, "Still no Cards Left", MasterGUI.black_gray,20f);
                    answer.setBounds(point.x+575, point.y, 499, 449);
                    //learnPanel.removeAll();
                    learnPanel.add(question);
                    learnPanel.add(questionPanel);
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
                /*if(card==null){
                    System.out.println("no cards left"); //<<------------------------------------- ab hier switch back
                    question = new Label(point.x, point.y+5 , "No Cards Left", MasterGUI.black_gray, 20f);
                    question.setBounds(point.x, point.y, 499, 449);
                    answer = new Label(point.x + 575, point.y, "Still no Cards Left", MasterGUI.black_gray,20f);
                    answer.setBounds(point.x+575, point.y, 499, 449);
                    //learnPanel.removeAll();
                    learnPanel.add(question);
                    learnPanel.add(questionPanel);
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
                //if card.isForgotten()==false card.setForgotten()
                else{
                    card.setDueTime(0);
                    queue.add(card);
                    System.out.println("hard");
                    createQA();
                }*/
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
}
