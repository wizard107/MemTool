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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.color.*;
import Model.Deck;
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
    private Deck deck;
    private static Panel superPanel;
    private static Panel subPanel;
    private int currentID = -1;
    private static int count = 0;
    private String[] questions = new String[11];
    private String[] answers = new String[11];
    TextPane questionPanelQA;
    TextPane answerPanelQA;
    private static JScrollPane scroller;
    public CardView(JFrame frame, Deck deck){
        super(frame);
        this.frame = frame;
        this.deck  = deck;
        for(int i=0;i<10;i++){
            questions[i] = "question" + String.valueOf(i);
            answers[i] = "answer" + String.valueOf(i);
        }
        superPanel = new Panel();
        superPanel.setPreferredSize(new Dimension(frame.getWidth()/2 + 70, frame.getHeight()*2+50));
        //superPanel.setBounds(0,0,frame.getWidth(), frame.getHeight());
        superPanel.setBackground(MasterGUI.babyblue);
        subPanel = new Panel();
        subPanel.setBounds(frame.getWidth()/2 + 70,0,frame.getWidth()/2, frame.getHeight());
        subPanel.setBackground(MasterGUI.babyred);
        Label warning2 = new Label(60, 130, "TO SAVE CHANGES PRESS SAVE", MasterGUI.purple, 30f);
        warning2.setSize(1000, 50);
        Label warning3 = new Label(20, 360, "SCROLL DOWN TO EDIT", MasterGUI.purple, 30f);
        warning3.setSize(350, 50);
        drawCards();
        scroller = makeScroller(superPanel);
        //add(warning1);
       // add(warning2);
        //add(warning3);
        add(questionPanelQA);
        add(answerPanelQA);
        add(subPanel);
        
        add(scroller);
        createWarning();
        //frame.add(y);
        //frame.setBackground(MasterGUI.babyred);
        
    }
    private void drawCards(){
        questionPanelQA = new TextPane(point.x + 630, point.y - 40,500, 270);
        answerPanelQA = new TextPane(point.x + 630, point.y + 260, 500, 270);
        questionPanelQA.setText("edit your question");
        answerPanelQA.setText("edit your answer");
        //questionPanelQA.setBounds(point.x + 600, point.y - 40, 500, 270);
        //answerPanelQA.setBounds(point.x + 600, point.y + 260, 500, 270);
        questionPanelQA.setBorder(BorderFactory.createLineBorder(Color.black));
        answerPanelQA.setBorder(BorderFactory.createLineBorder(Color.black));
        Point point2 = new Point(50,100);
        Panel idPanel = new Panel();
        Label title = new Label(point2.x-45, point2.y-90, " TITLE", MasterGUI.black,25f);
        title.setSize(500, 50);
        idPanel.setBounds(point2.x-40, point2.y-40, 385,25);
        Label idlabel = new Label(point2.x-40, point2.y-40, " ID", MasterGUI.black,15f);
        idlabel.setSize(35, 25);
        idlabel.setBorder(BorderFactory.createLineBorder(Color.black));
        Label namelabel = new Label(point2.x-5, point2.y-40, " NAME", MasterGUI.black,15f);
        namelabel.setSize(250, 25);
        namelabel.setBorder(BorderFactory.createLineBorder(Color.black));
        Label duelabel = new Label(point2.x+245, point2.y-40, " DUEDATE", MasterGUI.black,15f);
        duelabel.setSize(100, 25);
        duelabel.setBorder(BorderFactory.createLineBorder(Color.black));
        //Label decklabel = new Label(point2.x+345, point2.y-40, " DECK", MasterGUI.black,15f);
        //decklabel.setSize(155, 25);
        //decklabel.setBorder(BorderFactory.createLineBorder(Color.black));
        idPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        superPanel.add(title);
        superPanel.add(idlabel);
        superPanel.add(namelabel);
        superPanel.add(duelabel);
        //superPanel.add(decklabel);
        superPanel.add(idPanel);
        subPanel.add(questionPanelQA);
        subPanel.add(answerPanelQA);
        //superPanel.add(questionPanelQA);
        //superPanel.add(answerPanelQA);
        int cardSpace = 35;
        for(int i = 0;i<10;i++){
            Button edit = new Button(point2.x+505, point2.y-40 + cardSpace,"EDIT", MasterGUI.purple,80,25);   //später counter für actionlistener, der immer um eins hochgeht, jeder edit übernimmt dann die kontrolle für deck[count++]
            edit.setFont(MasterGUI.poppinsFont.deriveFont(15f));
            edit.setForeground(Color.WHITE);
            edit.setFocusPainted(false);
            edit.setContentAreaFilled(true);
            edit.setMargin(new Insets(5, 5, 3, 3));
            switchQA(edit, i);
            Button save = new Button(point2.x+400, point2.y-40 + cardSpace,"SAVE", MasterGUI.yellow,80,25);   
            save.setFont(MasterGUI.poppinsFont.deriveFont(15f));
            save.setForeground(Color.WHITE);
            save.setFocusPainted(false);
            save.setContentAreaFilled(true);
            save.setMargin(new Insets(5, 5, 3, 3));
            saveQA(save, i);
            idPanel = new Panel();
            idPanel.setBounds(point2.x-40, point2.y-40+ cardSpace, 385,25);
            idlabel = new Label(point2.x-40, point2.y-40+ cardSpace, " IDT", MasterGUI.black,15f);
            idlabel.setSize(35, 25);
            idlabel.setBorder(BorderFactory.createLineBorder(Color.black));
            namelabel = new Label(point2.x-5, point2.y-40+ cardSpace, " NAMET", MasterGUI.black,15f);
            namelabel.setSize(250, 25);
            namelabel.setBorder(BorderFactory.createLineBorder(Color.black));
            duelabel = new Label(point2.x+245, point2.y-40+ cardSpace, " DUEDATET", MasterGUI.black,15f);
            duelabel.setSize(100, 25);
            duelabel.setBorder(BorderFactory.createLineBorder(Color.black));
            idPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            superPanel.add(save);
            superPanel.add(idlabel);
            superPanel.add(namelabel);
            superPanel.add(duelabel);
           // superPanel.add(decklabel);
            superPanel.add(idPanel);
            superPanel.add(edit);
            cardSpace+=35;
            count++;
        }
    }

    private void switchQA(Button editBtn, int i){
        ActionListener change = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                questionPanelQA.removeAll();
                answerPanelQA.removeAll();
                questionPanelQA.setText(questions[i]);
                answerPanelQA.setText(answers[i]);
                currentID = i;
                System.out.println("Edit"); 
            }
        };
        editBtn.addActionListener(change);
    }
    private void saveQA(Button saveBtn, int i){
        ActionListener saveInput = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(currentID==-1) System.out.println("Not Saved");
                else{
                    questions[currentID] = questionPanelQA.getText();
                    answers[currentID] = answerPanelQA.getText();    
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
