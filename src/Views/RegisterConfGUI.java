package Views;
import Controller.*;
import Model.*;
import javax.swing.*;
import Views.Components.Button;
import Views.Components.Label;
import Views.Components.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;
import java.awt.Point;
import java.awt.Color;

public class RegisterConfGUI extends MasterGUI{
    protected static TextField regCodeField;
    protected static Button signUpBtn;
    protected static Button backBtn;
    protected static Label success;
    protected JLabel lockImage;
    protected Label screenTitle;
    protected Label screenDescription;

    private String inputRegCode;
    private String genRegCode="hi";


    protected static Point rgsBox = new Point(70, 70);
    public RegisterConfGUI(){

        setTitle("Registration Form");
        setSize(1000,500);
        panel.updateBounds(this);
        //panel.setBackground(new Color(55, 55, 70));
        setLocationRelativeTo(null);
        //setComponentStyles(panel,null);

        createRegistrationCodeFields();
        backBtnAction();
        signUpBtnAction();
        
        setComponentStyles(panel, "purple");
        signUpBtn.setFont(poppinsFontBig);
        backBtn.setFont(poppinsFontBig);
        setLocationRelativeTo(null);
        setVisible(true);
    } 

    private void createRegistrationCodeFields(){
        regCodeField = new TextField(rgsBox.x, rgsBox.y + 50);
        lockImage = new JLabel(lockPNG);
        lockImage.setBounds(rgsBox.x + 405,rgsBox.y -140, 500, 500);
        signUpBtn = new Button(rgsBox.x+680, rgsBox.y+150 + 180, "Sign Up", new Color(252,208,44), 210, 40);
        backBtn = new Button(rgsBox.x+450, rgsBox.y+150 + 180, "BACK",MasterGUI.purple,210,40);
        success = new Label(rgsBox.x, rgsBox.y + 330, "", null);
        screenTitle = new Label(rgsBox.x, rgsBox.y -50, "Almost done!", 1, MasterGUI.purple);
        screenDescription = new Label(rgsBox.x -10, rgsBox.y -20, "  Please enter your personal registration code",3, MasterGUI.purple);


        regCodeField.setSize(210, regCodeField.getHeight());
        regCodeField.setCaretColor(Color.WHITE);
        

        placeFieldLabel(regCodeField, "Registration code", panel);
  
        panel.add(regCodeField);
        panel.add(signUpBtn);
        panel.add(backBtn);
        panel.add(success);
        panel.add(screenTitle);
        panel.add(screenDescription);
        panel.add(lockImage);
      }    

  private void validateRegistrationCode() {
    inputRegCode = regCodeField.getText();
    if (inputRegCode.isBlank()) {
      success.setText("Registration code field is empty");
      return;
      
    } else if(!inputRegCode.equals(genRegCode)) {
      success.setText("Registration codes don't match");
      return;
    }
  }
  
 public void signUpBtnAction() {
    signUpBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          validateRegistrationCode();
          panel.removeAll();
          LoginGUI login = new LoginGUI();
          login.setVisible(true);
          success.setText("Account created");
          dispose();
      }
    });
  }

  public void backBtnAction() {
    backBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        panel.removeAll();
        dispose();
        LoginGUI login = new LoginGUI();
        login.setVisible(true);
      }
    });
  }


  public static void main(String[] args) {
    RegisterConfGUI registerconf = new RegisterConfGUI();
    registerconf.setVisible(true);
  }

}

