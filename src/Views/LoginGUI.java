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
  protected JLabel backIconHero;
  protected Label screenTitle;
  
  /** Pixel coordinates box for content */
  protected static Point lgnBox = new Point(70, 70);
    public LoginGUI() {
        setTitle("Scheduler Login");
        setSize(600, 500);
        panel.updateBounds(this);
        
        createLoginFields();
        //registerBtnAction();
        //loginBtnAction();
        //getRootPane().setDefaultButton(loginBtn);
        
        setComponentStyles(panel, "dark");
        //screenTitle.setHeading();
        setLocationRelativeTo(null);
        setVisible(true);
      }
    private void createLoginFields(){
      userField = new TextField(lgnBox.x, lgnBox.y + 50);
      passField = new JPasswordField();
      loginBtn = new Button(lgnBox.x, lgnBox.y + 180, "Login", new Color(116, 207, 183));
      registerBtn = new Button(lgnBox.x + 110, lgnBox.y + 180, "Sign Up");
      success = new Label(lgnBox.x, lgnBox.y + 250, "");
      //backIconHero = new JLabel(loginHeroImage);
      screenTitle = new Label(lgnBox.x, lgnBox.y - 10, "Login");
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
      //panel.add(backIconHero);
      panel.add(screenTitle);
    }
}