package Views.HomeViews;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Controller.DatabaseAPI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.awt.color.*;

import Model.Card;
import Model.Deck;
import Model.User;
import Views.MainGUI;
import Views.MasterGUI;
import Views.Components.Button;
import Views.Components.Label;
import Views.Components.Panel;
import Views.Components.TextField;
import Views.Components.TextPane;


public class CardView extends Panel{
    private static JFrame frame;
    private Point point = new Point(50 , 50);
    private Point point2 = new Point(50,100);
    private Deck deck;
    private static Panel superPanel;
    private static Panel subPanel;
    private int currentID = -1; //if currentID of card is -1 it is not saved, otherwise saves the id of currentcard
    private static int deckSize = 9;
    private int cardSpace = 35;
    private User user;
    //private String[] questions = new String[15];
    //private String[] answers = new String[15];
    private List<String> questions = new ArrayList<String>();
    private List<String> answers = new ArrayList<String>();
    private ArrayList<Card> cards = new ArrayList<Card>();
    private Button save;
    private TextPane questionPanelQA;
    private TextPane answerPanelQA;
    private static JScrollPane scroller;

    public CardView(JFrame frame, Deck deck, User user){
        super(frame);
        this.frame = frame;
        this.deck  = deck;
        this.user = user;
        cards = deck.getCards();
        
        superPanel = new Panel();
        superPanel.setPreferredSize(new Dimension(frame.getWidth()/2 + 70, frame.getHeight()*2+50));
        superPanel.setBackground(MasterGUI.babyblue);
        subPanel = new Panel();
        subPanel.setBounds(frame.getWidth()/2 + 70,0,frame.getWidth()/2, frame.getHeight());
        subPanel.setBackground(MasterGUI.babyred);
        Label warning2 = new Label(60, 130, "TO SAVE CHANGES PRESS SAVE", MasterGUI.purple, 30f);
        warning2.setSize(1000, 50);
        Label warning3 = new Label(20, 360, "SCROLL DOWN TO EDIT", MasterGUI.purple, 30f);
        warning3.setSize(350, 50);
        drawRightSide();
        scroller = makeScroller(superPanel);
        add(save);
        add(questionPanelQA);
        add(answerPanelQA);
        add(subPanel);
        
        
        add(scroller);
        createWarning();
        
    }
    private void drawRightSide(){
        
        questionPanelQA = new TextPane(point.x + 630, point.y - 40,500, 270);
        answerPanelQA = new TextPane(point.x + 630, point.y + 260, 500, 270);
        questionPanelQA.setText("edit your question");
        answerPanelQA.setText("edit your answer");
        questionPanelQA.setBorder(BorderFactory.createLineBorder(Color.black));
        answerPanelQA.setBorder(BorderFactory.createLineBorder(Color.black));
        save = new Button(point.x+630, point2.y+183 ,"SAVE", MasterGUI.black_gray,80,25);   
        createCardViewBtn(save);
        saveQA(save);
        drawLeftSide();
        
    }
    public void drawLeftSide(){
        Button addBtn = new Button(point.x+505, point.y+10,"ADD", MasterGUI.yellow,80,25);   //später counter für actionlistener, der immer um eins hochgeht, jeder edit übernimmt dann die kontrolle für deck[count++]
        addBtn.setFont(MasterGUI.poppinsFont.deriveFont(15f));
        createCardViewBtn(addBtn);
        addQA(addBtn);
        Panel idPanel = new Panel();
        idPanel.setBounds(point2.x-40, point2.y-40, 385,25);
        idPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        Label title = new Label(point2.x-45, point2.y-90, " TITLE", MasterGUI.black,25f);
        title.setSize(500, 50);
        Label idlabel = new Label(point2.x-40, point2.y-40, " ID", MasterGUI.black,15f);
        createCardViewLabel(idlabel,35, 25);
        idlabel.setBorder(BorderFactory.createLineBorder(Color.black));
        Label namelabel = new Label(point2.x-5, point2.y-40, " NAME", MasterGUI.black,15f);
        createCardViewLabel(namelabel,250, 25);
        Label duelabel = new Label(point2.x+245, point2.y-40, " DUEDATE", MasterGUI.black,15f);
        createCardViewLabel(duelabel, 100, 25);
        
        superPanel.add(title);
        superPanel.add(idlabel);
        superPanel.add(namelabel);
        superPanel.add(duelabel);
        superPanel.add(addBtn);
        superPanel.add(idPanel);

        
        for(int i = 0;i<cards.size();i++){
            Card card = cards.get(i);
            Button edit = new Button(point2.x+505, point2.y-40 + cardSpace,"EDIT", MasterGUI.purple,80,25);   //später counter für actionlistener, der immer um eins hochgeht, jeder edit übernimmt dann die kontrolle für deck[count++]
            createCardViewBtn(edit);
            switchQA(edit, i);
            Button delete = new Button(point2.x+400, point2.y-40 + cardSpace,"DELETE", MasterGUI.red,80,25);   
            createCardViewBtn(delete);
            deleteQA(delete, i);
            idPanel = new Panel();
            idPanel.setBounds(point2.x-40, point2.y-40+ cardSpace, 385,25);
            idlabel = new Label(point2.x-40, point2.y-40+ cardSpace, String.valueOf(card.getCardPosition()), MasterGUI.black,15f);
            createCardViewLabel(idlabel,35, 25);
            namelabel = new Label(point2.x-5, point2.y-40+ cardSpace, card.getFrontText(), MasterGUI.black,15f);
            createCardViewLabel(namelabel, 250, 25);
            duelabel = new Label(point2.x+245, point2.y-40+ cardSpace, card.getDueDate().format(DateTimeFormatter.ofPattern("dd-MMM-yy")), MasterGUI.black,15f);
            createCardViewLabel(duelabel,100, 25);
            idPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            superPanel.add(delete);
            superPanel.add(idlabel);
            superPanel.add(namelabel);
            superPanel.add(duelabel);
            superPanel.add(idPanel);
            superPanel.add(edit);
            cardSpace+=35;
        }
    }
    public static void createCardViewBtn(Button btn){
        btn.setFont(MasterGUI.poppinsFont.deriveFont(15f));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setMargin(new Insets(5, 5, 3, 3));
    }

    public static void createCardViewLabel(Label label, int w, int h){
        label.setSize(w, h);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
    }
    private void addQA(Button addBtn){
        ActionListener adding = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                questionPanelQA.removeAll();
                answerPanelQA.removeAll();
                questionPanelQA.setText("New Card Added\nPRESS EDIT");
                answerPanelQA.setText("New Card Added\nPRESS EDIT");
                Card addCard = new Card(deck.getId(), "New Card Added", "New Card Added",0, 0,LocalDate.now(), false,true);
                addCard.setId(DatabaseAPI.storeCard(addCard));
                cards.add(addCard);
                deck.setCardDeck(cards);
                user.setMainDeck(deck.getDeckPosition(), deck);
                user.initializeCardPositions(deck.getDeckPosition());
                
                Panel idPanel = new Panel();
                idPanel.setBounds(point2.x-40, point2.y-40+ cardSpace, 385,25);
                idPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                Label idlabel = new Label(point2.x-40, point2.y-40+ cardSpace, String.valueOf(addCard.getCardPosition()), MasterGUI.black,15f);
                createCardViewLabel(idlabel,35, 25);
                Label namelabel = new Label(point2.x-5, point2.y-40+ cardSpace, addCard.getFrontText(), MasterGUI.black,15f);
                createCardViewLabel(namelabel,250, 25);
                Label duelabel = new Label(point2.x+245, point2.y-40+ cardSpace, addCard.getDueDate().format(DateTimeFormatter.ofPattern("dd-MMM-yy")), MasterGUI.black,15f);
                createCardViewLabel(duelabel,100, 25);
                
                superPanel.add(idlabel);
                superPanel.add(namelabel);
                superPanel.add(duelabel);
                superPanel.add(idPanel);
                Button edit = new Button(point2.x+505, point2.y-40 + cardSpace,"EDIT", MasterGUI.purple,80,25);   //später counter für actionlistener, der immer um eins hochgeht, jeder edit übernimmt dann die kontrolle für deck[count++]
                createCardViewBtn(edit);
                Button delete = new Button(point2.x+400, point2.y-40 + cardSpace,"DELETE", MasterGUI.red,80,25);   
                createCardViewBtn(delete);
                deleteQA(delete, cards.size()-1);
                switchQA(edit, cards.size()-1);
                superPanel.add(edit);
                superPanel.add(delete);
                superPanel.repaint();
                cardSpace+=35;
                System.out.println("Added"); 
                HomeView.repaintHomeView();
            }
        };
        addBtn.addActionListener(adding);
    }
    private void deleteQA(Button deleteBtn, int i){
        ActionListener deletion = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                questionPanelQA.removeAll();
                answerPanelQA.removeAll();
                Card del = cards.get(i);
                cards.remove(i);
                superPanel.removeAll();
                cardSpace = 35;
                deck.setCardDeck(cards);
                user.setMainDeck(deck.getDeckPosition(), deck);
                user.initializeCardPositions(deck.getDeckPosition());
                DatabaseAPI.deleteCard(del.getId());
                currentID = -1;
                drawLeftSide();
                superPanel.repaint();
                System.out.println("Deleted");
                HomeView.repaintHomeView();

            }
        };
        deleteBtn.addActionListener(deletion);
    }
    private void switchQA(Button editBtn, int i){
        ActionListener change = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Card card = cards.get(i);
                questionPanelQA.removeAll();
                answerPanelQA.removeAll(); 
                questionPanelQA.setText(card.getFrontText());
                answerPanelQA.setText(card.getBackText());
                currentID = i;
                System.out.println("Edit"); 
            }
        };
        editBtn.addActionListener(change);
    }
    private void saveQA(Button saveBtn){
        ActionListener saveInput = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(currentID==-1) System.out.println("Not Saved");
                else{
                    String store[] = questionPanelQA.getText().split("\n");
                    String txtQ = store[0];
                    if(store.length>1){
                        for(int i=0;i<store.length;i++){
                            if(i>0)txtQ += "\n";
                            txtQ += store[i];
                        }
                    }
                   
                    String store2[] = answerPanelQA.getText().split("\n");
                    String txtA = store2[0];
                    if(store2.length>1){
                        for(int i=0;i<store2.length;i++){
                            if(i>0)txtA += "\n";
                            txtA += store2[i];
                        }
                    }
                    
                    cards.get(currentID).setFrontText(txtQ);
                    cards.get(currentID).setBackText(txtA);
                    deck.setCardDeck(cards);
                    user.setMainDeck(deck.getDeckPosition(), deck);
                    DatabaseAPI.editCard(cards.get(currentID));
                  
                    superPanel.removeAll();
                    cardSpace = 35;
                    drawLeftSide();
                    superPanel.repaint(); 
                    System.out.println("Saved");          
                }
            }
        };
        saveBtn.addActionListener(saveInput);
    }

    private void createWarning(){
        Panel questionPanel = new Panel();
        Panel answerPanel = new Panel();
        Panel idPanel = new Panel();
        questionPanel.setBounds(point.x + 600, point.y - 40, 500, 270);
        answerPanel.setBounds(point.x + 600, point.y + 260, 500, 270);
        idPanel.setBounds(point.x-40, point.y-40, 540,25);
        Label idlabel = new Label(point.x-40, point.y-40, " ID", MasterGUI.black,15f);
        idlabel.setSize(35, 25);
        idlabel.setBorder(BorderFactory.createLineBorder(Color.black));
        Label namelabel = new Label(point.x-5, point.y-40, " NAME", MasterGUI.black,15f);
        namelabel.setSize(250, 25);
        namelabel.setBorder(BorderFactory.createLineBorder(Color.black));
        Label duelabel = new Label(point.x+245, point.y-40, " DUEDATE", MasterGUI.black,15f);
        duelabel.setSize(100, 25);
        duelabel.setBorder(BorderFactory.createLineBorder(Color.black));
        Label decklabel = new Label(point.x+345, point.y-40, " DECK", MasterGUI.black,15f);
        decklabel.setSize(155, 25);
        decklabel.setBorder(BorderFactory.createLineBorder(Color.black));
        Label editq = new Label(point.x + 600, point.y-120, "EDIT YOUR QUESTION...", MasterGUI.black,30f);
        editq.setSize(1000, 200);
        Label edita = new Label(point.x + 600, point.y + 180, "EDIT YOUR ANSWER...", MasterGUI.black,30f);
        edita.setSize(1000, 200);
        JLabel stop = new JLabel(MasterGUI.stopPNG);
        stop.setBounds(point.x + 300, point.y, 200, 800);
        idPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        questionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        answerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        scroller.add(stop);
        scroller.add(edita);
        scroller.add(editq);
        scroller.add(idlabel);
        scroller.add(namelabel);
        scroller.add(duelabel);
        scroller.add(decklabel);
        scroller.add(idPanel);
        scroller.add(questionPanel);
        scroller.add(answerPanel);
    }
    private JScrollPane makeScroller(Panel panel){
        scroller = new JScrollPane(panel);
        scroller.setBounds(0,0,(getWidth()+1)/2 + 70,getHeight());
        scroller.getVerticalScrollBar().setUnitIncrement(10);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setPreferredSize(new Dimension(this.getWidth()/2 + 70, this.getHeight()));
        scroller.setBackground(MasterGUI.babyred);
        return scroller;
    }
}
