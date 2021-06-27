package Views.HomeViews;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import Controller.DatabaseAPI;
import Controller.PasswordEncryption;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import Model.User;
import Views.MasterGUI;
import Views.Components.Button;
import Views.Components.Label;
import Views.Components.Panel;
import Views.Components.TextField;

public class ProfileView extends Panel{
    private Panel profilePanel;
    private TextField nameField;
    private JPasswordField pwField;
    private JPasswordField confirmpwField;
    private TextField emailField;
    private Label name;
    private Label pw;
    private Label confirmpw;
    private JLabel dragonImage;
    private Label email;
    private Label wrongpw;
    private Label wrongemail;
    private Button save;
    private User user;
    private Point point = new Point(50,50);
    public ProfileView(JFrame frame, User user){
        super(frame);
        this.user = user;
        profilePanel = new Panel();
        profilePanel.setBounds(0,0, frame.getWidth(), frame.getHeight());
        profilePanel.setBackground(MasterGUI.babygreen);
        Label title = new Label(point.x + 60, point.y , "EDIT YOUR PROFILE", MasterGUI.black, 30f);
        title.setSize(500,70);
        title.setForeground(MasterGUI.black);
        //JLabel pcImage = new JLabel(MasterGUI.pcPNG);
        //pcImage.setBounds(point.x + 600, point.y-100, 700, 700);
        //profilePanel.add(pcImage);
        profilePanel.add(title);
        createView();
        add(profilePanel);
        
    }
    public void createView(){
        Label change = new Label(point.x + 60, point.y+60 , "Change your data as you like", MasterGUI.black, 20f);
        change.setSize(500,50);
        change.setForeground(MasterGUI.black);
        save = new Button(point.x + 60, point.y+370, "SAVE",MasterGUI.black_gray, 110,40);
        createProfileViewBtn(save);
        name = new Label(point.x + 60, point.y+210 , "Username", MasterGUI.black, 20f);
        name.setForeground(MasterGUI.black);
        email = new Label(point.x + 60, point.y+290 , "Email", MasterGUI.black, 20f);
        email.setForeground(MasterGUI.black);
        pw = new Label(point.x + 380, point.y+210 , "New Password", MasterGUI.black, 20f);
        pw.setForeground(MasterGUI.black);
        confirmpw = new Label(point.x + 380, point.y+290 , "Confirm Password", MasterGUI.black, 20f);
        confirmpw.setForeground(MasterGUI.black);
        nameField = new TextField(point.x + 60, point.y+240);
        nameField.setFont(MasterGUI.poppinsFont.deriveFont(15f));
        pwField = new JPasswordField();
        pwField.setBounds(point.x + 380, point.y+240, 310, 40);
        wrongpw = new Label(point.x + 700, point.y+240 , "Passwords do not match", MasterGUI.black, 20f);
        wrongpw.setSize(500,50);
        wrongpw.setForeground(MasterGUI.black);
        confirmpwField = new JPasswordField();
        confirmpwField.setBounds(point.x + 380, point.y+320, 310, 40);
        wrongemail = new Label(point.x + 700, point.y+320 , "Email wrong", MasterGUI.black, 20f);
        wrongemail.setForeground(MasterGUI.black);
        wrongemail.setSize(500,50);
        pwField.setCaretColor(new Color(60, 60, 75));
        pwField.setBackground(new Color(240, 240, 245));
        pwField.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        confirmpwField.setCaretColor(new Color(60, 60, 75));
        confirmpwField.setBackground(new Color(240, 240, 245));
        confirmpwField.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        emailField = new TextField(point.x + 60, point.y+320);
        emailField.setFont(MasterGUI.poppinsFont.deriveFont(15f));
        dragonImage = new JLabel(MasterGUI.dragonPNG);
        dragonImage.setBounds(480, -125, 700, 500);
        emailField.setText(user.getEmail());
        nameField.setText(user.getUsername());
        
        profilePanel.add(change);
        profilePanel.add(name);
        profilePanel.add(email);
        profilePanel.add(pw);
        profilePanel.add(confirmpw);
        profilePanel.add(confirmpwField);
        profilePanel.add(save);
        profilePanel.add(nameField);
        profilePanel.add(pwField);
        profilePanel.add(emailField);
        addSave();

    }
    public static void createProfileViewBtn(Button btn){
        btn.setFont(MasterGUI.poppinsFont.deriveFont(20f));
        btn.setForeground(MasterGUI.white);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setMargin(new Insets(5, 5, 3, 3));
    }
    public void addSave(){
        ActionListener saveChanges = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                profilePanel.remove(wrongemail);
                profilePanel.remove(wrongpw);
                user.setUsername(nameField.getText());
                if(!(pwField.getPassword()==null)){
                    if(validatePW()){
                        String newPW = PasswordEncryption.createHash(pwField.getPassword().toString());
                        user.setPassword(newPW);
                    }
                    else{
                        profilePanel.add(wrongpw);
                        
                    } 
                }
                if(validateEmail()) user.setEmail(emailField.getText());
                else{
                    profilePanel.add(wrongemail);
                } 
                DatabaseAPI.editUser(user);
                HomeView.repaintHomeView();
                System.out.println("User saved");
            }
        };
        save.addActionListener(saveChanges);
    }
    private boolean validateEmail(){
        String inputMail = emailField.getText();
        if((inputMail.contains("@")&&inputMail.contains("."))){
            return true;
        }
        else return false;
    }
    private boolean validatePW(){
        String newPW = String.valueOf(pwField.getPassword());
        String confirmnewPW = String.valueOf(confirmpwField.getPassword());
        if(newPW.equals(confirmnewPW)) return true;
        else return false;
    }
}
