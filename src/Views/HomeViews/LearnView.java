package Views.HomeViews;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Point;
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
    private Label question;
    private Label answer;
    private Button revealBtn;
    private Button easy;
    private Button medium;
    private Button difficult;

    public LearnView(JFrame frame, Deck deck){
        super(frame);
        this.frame = frame;
        this.deck  = deck;
        questionPanel = new Panel();
        answerPanel = new Panel();
        questionPanel.setBounds(point.x, point.y, 500, 450);
        answerPanel.setBounds(point.x + 575, point.y, 500, 450);
        learnPanel = new Panel();
        learnPanel.setBounds(0,0, frame.getWidth(), frame.getHeight());
        //learnPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()*2+50));
        learnPanel.setBackground(MasterGUI.black);
        
        String[] testq = new String[3];
        String[] testa = new String[3];

        testq[0] = "test1";
        testq[1] = "test2";
        testq[2] = "test3";
        testa[0] = "test1";
        testa[1] = "test2";
        testa[2] = "test3";
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
        Label title = new Label(point.x, point.y -30 , "TITLE", MasterGUI.white, 24f);
        Label cardsLeft = new Label(point.x + 825, point.y -30 , "CARDS LEFT:", MasterGUI.white, 24f);
        Label due0 = new Label(point.x + 995, point.y -30 , "0", MasterGUI.green, 20f);
        Label failed0 = new Label(point.x + 1030, point.y -30 , "0", MasterGUI.red, 20f);
        Label neu0 = new Label(point.x + 1065, point.y -30 , "0", MasterGUI.blue, 20f);

        learnPanel.add(title);
        learnPanel.add(cardsLeft);
        learnPanel.add(due0);
        learnPanel.add(failed0);
        learnPanel.add(neu0);
        
        }
    private void createQA(){
        question = new Label(point.x, point.y+5 , "test" + String.valueOf(count), MasterGUI.black_gray, 20f);
        question.setBounds(point.x, point.y, 499, 449);
        answer = new Label(point.x + 575, point.y, "test" + String.valueOf(count++), MasterGUI.black_gray,20f);
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

                learnPanel.remove(questionPanel);
                learnPanel.remove(answerPanel);
                learnPanel.remove(question);
                learnPanel.remove(answer);
                System.out.println("easy");
                createQA();
            }
        };
        ActionListener changeCardMedium = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                learnPanel.remove(questionPanel);
                learnPanel.remove(answerPanel);
                learnPanel.remove(question);
                learnPanel.remove(answer);
                System.out.println("medium");
                createQA();
            }
        };
        ActionListener changeCardDifficult = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                learnPanel.remove(questionPanel);
                learnPanel.remove(answerPanel);
                learnPanel.remove(question);
                learnPanel.remove(answer);
                System.out.println("hard");
                createQA();
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
