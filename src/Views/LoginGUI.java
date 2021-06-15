package Views;
import javax.swing.*;

import Views.Components.Button;
import Views.Components.Label;
import Views.Components.TextField;

import java.awt.Point;
import java.awt.Color;

public class LoginGUI extends MasterGUI{
  protected static TextField userField;
  protected static JPasswordField passField;
  protected static Button loginBtn;
  protected static Button registerBtn;
  protected static Label success;
  protected JLabel learnImage;
  protected Label screenTitle;
  protected Label screenDescription;
  
  /** Pixel coordinates box for content */
  protected static Point lgnBox = new Point(70, 70);
    public LoginGUI() {
        setTitle("Scheduler Login");
        setSize(1000, 500);
        panel.updateBounds(this);
        
        createLoginFields();
        //registerBtnAction();
        //loginBtnAction();
        //getRootPane().setDefaultButton(loginBtn);
        
        setComponentStyles(panel, "purple");
        loginBtn.setFont(poppinsFontBig);
        registerBtn.setFont(poppinsFontBig);
        setLocationRelativeTo(null);
        setVisible(true);
      }
    private void createLoginFields(){
      userField = new TextField(lgnBox.x, lgnBox.y + 50);
      passField = new JPasswordField();
      //loginBtn = new Button(lgnBox.x, lgnBox.y + 180, "Login", new Color(116, 207, 183));
      loginBtn = new Button(lgnBox.x, lgnBox.y + 180, "LOGIN", new Color(252,208,44), 210, 40);

      learnImage = new JLabel(learnPNG);
      learnImage.setBounds(250, -125, 700, 700);
      registerBtn = new Button(lgnBox.x, lgnBox.y + 230, "REGISTER",MasterGUI.purple,210,40);
      success = new Label(lgnBox.x, lgnBox.y + 250, "", null);
      //backIconHero = new JLabel(loginHeroImage);
      screenTitle = new Label(lgnBox.x, lgnBox.y -50, "WELCOME BACK", 1, MasterGUI.purple);
      screenDescription = new Label(lgnBox.x -10, lgnBox.y -20, "Ready to learn something new today?",3, MasterGUI.purple);
      //backIconHero.setBounds(200, 250, 400, 400);
      userField.setSize(210, userField.getHeight());
      //userField.setSize(210, 40);
      userField.setCaretColor(Color.WHITE);
      passField.setBounds(lgnBox.x, lgnBox.y + 120, 210, 40);
      placeFieldLabel(userField, "Username", panel);
      placeFieldLabel(passField, "Password", panel);

      panel.add(userField);
      panel.add(passField);
      panel.add(loginBtn);
      panel.add(registerBtn);
      panel.add(success);
      panel.add(learnImage);
      panel.add(screenTitle);
      panel.add(screenDescription);
    }
}