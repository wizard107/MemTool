package Views.HomeViews;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Controller.DatabaseAPI;
import Model.User;
import Views.MainGUI;
import Views.MasterGUI;
import Views.Components.Panel;
import Views.Components.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Color;
import Views.Components.Button;
import Views.Components.Label;
public class AdminView extends Panel {
    private User user;
    private Panel adminPanel;
    private Button searchBtn;
    private TextField searchField;
    private TextField nameField;
    private TextField pwField;
    private TextField emailField;
    private Label name;
    private Label pw;
    private Label email;
    private Button save;
    private Button delete;
    private User changeUser;
    Point point = new Point(50,50);
    public AdminView(JFrame frame, User currentUser){
        super(frame);
        this.user = user;
        adminPanel = new Panel();
        adminPanel.setBounds(0,0, frame.getWidth(), frame.getHeight());
        adminPanel.setBackground(MasterGUI.black);
        Label title = new Label(point.x + 60, point.y , "EDIT AND DELETE USERS", MasterGUI.black, 30f);
        title.setSize(500,70);
        title.setForeground(MasterGUI.white);
        JLabel pcImage = new JLabel(MasterGUI.pcPNG);
        pcImage.setBounds(point.x + 600, point.y-100, 700, 700);
        adminPanel.add(pcImage);
        adminPanel.add(title);
        createView();
        add(adminPanel);
        
    }
    public void createView(){
        Label search = new Label(point.x + 60, point.y+60 , "Search for Username", MasterGUI.black, 20f);
        search.setForeground(MasterGUI.white);
        searchField = new TextField(point.x + 60, point.y+100);
        searchField.setFont(MasterGUI.poppinsFont.deriveFont(15f));
        searchBtn = new Button(point.x + 60, point.y+155, "SEARCH",MasterGUI.purple, 110,40);
        createAdminViewBtn(searchBtn);
        save = new Button(point.x + 60, point.y+370, "SAVE",MasterGUI.yellow, 110,40);
        createAdminViewBtn(save);
        delete = new Button(point.x + 180, point.y+370, "DELETE",MasterGUI.red, 110,40);
        createAdminViewBtn(delete);
        name = new Label(point.x + 60, point.y+210 , "Username", MasterGUI.black, 20f);
        name.setForeground(MasterGUI.white);
        email = new Label(point.x + 60, point.y+290 , "Email", MasterGUI.black, 20f);
        email.setForeground(MasterGUI.white);
        pw = new Label(point.x + 380, point.y+210 , "Password", MasterGUI.black, 20f);
        pw.setForeground(MasterGUI.white);
        nameField = new TextField(point.x + 60, point.y+240);
        nameField.setFont(MasterGUI.poppinsFont.deriveFont(15f));
        pwField = new TextField(point.x + 380, point.y+240);
        pwField.setFont(MasterGUI.poppinsFont.deriveFont(15f));
        emailField = new TextField(point.x + 60, point.y+320);
        emailField.setFont(MasterGUI.poppinsFont.deriveFont(15f));
        addSearchBtnFunction();
        
        adminPanel.add(search);
        adminPanel.add(searchBtn);
        adminPanel.add(searchField);
    }
    public static void createAdminViewBtn(Button btn){
        btn.setFont(MasterGUI.poppinsFont.deriveFont(20f));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setMargin(new Insets(5, 5, 3, 3));
    }
    
    private void addSearchBtnFunction(){
        ActionListener search = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    System.out.println(searchField.getText());
                    changeUser = DatabaseAPI.getUser((searchField.getText()));
                    adminPanel.add(pw);
                    adminPanel.add(name);
                    adminPanel.add(email);
                    adminPanel.add(nameField);
                    adminPanel.add(pwField);
                    adminPanel.add(emailField);
                    adminPanel.add(save);
                    adminPanel.add(delete);
                    nameField.setText(changeUser.getUsername());
                    emailField.setText(changeUser.getEmail());
                    pwField.setText("Enter new Password");
                    adminPanel.repaint();
                }catch(NullPointerException ex){
                    System.out.println("No such user exists");
                    adminPanel.remove(pw);
                    adminPanel.remove(name);
                    adminPanel.remove(email);
                    adminPanel.remove(nameField);
                    adminPanel.remove(pwField);
                    adminPanel.remove(emailField);
                    adminPanel.remove(save);
                    adminPanel.remove(delete);
                    adminPanel.repaint();

                }
            }
        };
        searchBtn.addActionListener(search);
    }
    public void createUserTemplate(){
        System.out.println("user");
        
    }
    
    
}
