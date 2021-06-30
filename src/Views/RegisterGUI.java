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

public class RegisterGUI extends MasterGUI {
  protected static TextField firstnameField;
  protected static TextField lastnameField;
  protected static TextField emailField;
  protected static TextField userField;
  protected static TextField regCodeField;
  protected static JPasswordField passField;
  protected static JPasswordField passConfField;
  protected static Button signUpBtn;
  protected static Button backBtn;
  protected static Label success;
  protected static Label regInfo;
  protected JLabel lockImage;
  protected Label screenTitle;
  protected Label screenDescription;

  private String inputUser;
  private String inputPass;
  private String inputPassConf;
  private String inputEmail;
  private String encryptPass;
  private static int signupbtntype = 0;
  private String inputRegCode;
  private static String genRegCode;

  protected static Point rgsBox = new Point(70, 70);

  public RegisterGUI() {

    signupbtntype = 0;
    setTitle("Registration Form");
    setSize(1000, 500);
    panel.updateBounds(this);
    // panel.setBackground(new Color(55, 55, 70));
    setLocationRelativeTo(null);
    // setComponentStyles(panel,null);

    createRegistrationFields();
    backBtnAction();
    signUpBtnAction();

    setComponentStyles(panel, "purple");
    signUpBtn.setFont(poppinsFontBig);
    backBtn.setFont(poppinsFontBig);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void createRegistrationFields() {
    userField = new TextField(rgsBox.x, rgsBox.y + 50);
    passField = new JPasswordField();
    passConfField = new JPasswordField();
    emailField = new TextField(rgsBox.x, rgsBox.y + 50);
    regCodeField = new TextField(rgsBox.x, rgsBox.y + 50);
    lockImage = new JLabel(lockPNG);
    lockImage.setBounds(rgsBox.x + 405, rgsBox.y - 140, 500, 500);
    signUpBtn = new Button(rgsBox.x + 680, rgsBox.y + 150 + 180, "Sign Up", new Color(252, 208, 44), 210, 40);
    backBtn = new Button(rgsBox.x + 450, rgsBox.y + 150 + 180, "BACK", MasterGUI.purple, 210, 40);
    success = new Label(rgsBox.x, rgsBox.y + 330, "", null);
    regInfo = new Label(rgsBox.x, rgsBox.y + 260, "", null);
    screenTitle = new Label(rgsBox.x, rgsBox.y - 50, "Nice to see you!", 1, MasterGUI.purple);
    screenDescription = new Label(rgsBox.x - 10, rgsBox.y - 20, "  Please fillout all the required fields", 3,
        MasterGUI.purple);

    userField.setSize(210, userField.getHeight());
    userField.setCaretColor(Color.WHITE);
    passField.setBounds(rgsBox.x, rgsBox.y + 120, 210, 40);
    passConfField.setBounds(rgsBox.x + 225, rgsBox.y + 120, 210, 40);
    emailField.setBounds(rgsBox.x, rgsBox.y + 190, 210, 40);
    regCodeField.setBounds(rgsBox.x + 225, rgsBox.y + 190, 210, 40);

    placeFieldLabel(userField, "Username", panel);
    placeFieldLabel(passField, "Password", panel);
    placeFieldLabel(passConfField, "Confirm Password", panel);
    placeFieldLabel(emailField, "Email", panel);

    panel.add(userField);
    panel.add(passField);
    panel.add(passConfField);
    panel.add(emailField);
    panel.add(signUpBtn);
    panel.add(backBtn);
    panel.add(success);
    panel.add(regInfo);
    panel.add(screenTitle);
    panel.add(screenDescription);
    panel.add(lockImage);
  }

  private void validateForm() {
    inputUser = userField.getText();
    inputPass = String.valueOf(passField.getPassword());
    inputPassConf = String.valueOf(passConfField.getPassword());
    inputEmail = emailField.getText();

    if (inputUser.isBlank() || inputPass.isBlank() || inputEmail.isBlank()) {
      success.setText("Required fields missing");
      validateForm();
    } else if (!inputPassConf.equals(inputPass)) {
      success.setText("Passwords don't match");
      validateForm();
    } else if (!(inputEmail.contains("@") && inputEmail.contains("."))) {
      success.setText("Invalid email");
      validateForm();
    }
  }

  private void validateRegistrationCode() {
    inputRegCode = regCodeField.getText();
    if (inputRegCode.isBlank()) {
      success.setText("Please enter registration code");
      validateRegistrationCode();

    } else if (!inputRegCode.equals(RegisterGUI.genRegCode)) {
      success.setText("Registration codes don't match");
      validateRegistrationCode();
    }
  }

  private void processRegistration() {
    encryptPass = PasswordEncryption.createHash(inputPass);
    User user = new User(inputUser, encryptPass, inputEmail);
    if (!DatabaseAPI.isAvailable(user)) {
      success.setText("User already exists");
      return;
    }
    genRegCode = generateRegistrationCode();
    inputEmail = emailField.getText();
    adjustRegConGUI();
    EmailHandler.sendRegistrationMail(genRegCode, inputEmail);
  }

  private void adjustRegConGUI() {
    screenTitle.setText("   Almost done!");
    screenDescription.setText("We send a personal registration code");
    placeFieldLabel(regCodeField, "Registration code", panel);
    panel.add(regCodeField);
    userField.setEditable(false);
    passField.setEditable(false);
    passConfField.setEditable(false);
    emailField.setEditable(false);

    setLocationRelativeTo(null);
    setVisible(true);
    setComponentStyles(panel, "purple");
  }

  private String generateRegistrationCode() {
    final String ab = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    SecureRandom rnd = new SecureRandom();

    StringBuilder sb = new StringBuilder(10);
    for (int i = 0; i < 10; i++) {
      sb.append(ab.charAt(rnd.nextInt(ab.length())));
    }
    return sb.toString();
  }

  public void signUpBtnAction() {
    signUpBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (signupbtntype == 0) {
          validateForm();
          processRegistration();
          signupbtntype = 1;
        }
        if (signupbtntype == 1) {
          validateRegistrationCode();
          User user = new User(inputUser, encryptPass, inputEmail);
          DatabaseAPI.storeUser(user);
          signupbtntype = 0;
          panel.removeAll();
          LoginGUI login = new LoginGUI();
          login.setVisible(true);
          success.setText("Account created");
          dispose();
        }
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
    RegisterGUI register = new RegisterGUI();
    register.setVisible(true);
  }
}
