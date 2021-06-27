package Views;
import javax.swing.*;

import Controller.DatabaseAPI;
import Model.Deck;
import Model.User;
import Views.Components.Button;
import Views.Components.Label;
import Views.HomeViews.*;
import Views.*;
import Views.Components.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class MainGUI extends MasterGUI{
    private static JFrame frame;
    private Point tabPoint;
    private static JPanel sidebar = new JPanel();
    private static JPanel currentPanel;
    private static Button homeTab;
    private static Button deckTab;
    private static Button searchTab;
    private static Button logoutTab;
    private static Button profileTab;
    private static Button adminTab;
    private static Button inactiveTab;
    public static HomeView homePanel;
    public static DeckView deckPanel;
    public static SearchView searchPanel;
    public static ProfileView profilePanel;
    private User user;

    MainGUI(User user){
        frame = this;
        this.user = user;
        this.user.initializePositions();
        setTitle("MemTool");
        setSize(1200,700);
        remove(panel); // removes LoginGUI panel content as panel from mastergui still saved it
        
        tabPoint = new Point(0,0);
        homePanel = new HomeView(frame, user);//user muss geaddet werden
        deckPanel = new DeckView(frame, user);
        //searchPanel = new SearchView(frame);
        profilePanel = new ProfileView(frame, user);
        currentPanel = homePanel;
        
        add(homePanel);
        
        createSidebar();
        createAdminPanel();
        createSidebarTabs();
        createLogoutTab();
        
        setComponentStyles(panel, "light");
        add(sidebar);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createSidebar(){
        sidebar.setBackground(MasterGUI.purple.darker());
        sidebar.setBounds(0,0,this.getWidth(),50);
        sidebar.setLayout(null);
    }

    
    private void createSidebarTabs(){
        homeTab = new Button(tabPoint.x, tabPoint.y, "         Home", homePanel);
        deckTab = new Button(tabPoint.x + 730, tabPoint.y, "Manage Decks", deckPanel);
        //searchTab = new Button(tabPoint.x + 730, tabPoint.y, "         Search", searchPanel);
        profileTab = new Button(tabPoint.x + 860, tabPoint.y, "         Profile", profilePanel);
        //searchTab.setSize(65,50);
        //profileTab.setSize(65,50);
        sidebar.add(homeTab);
        sidebar.add(deckTab);
        sidebar.add(profileTab);
        Color active = MasterGUI.purple;
        Color inactive = MasterGUI.purple.darker();
        homeTab.setColor(active);
        inactiveTab = homeTab;
        List<Button> tabs = new ArrayList<>(
        Arrays.asList(homeTab, deckTab, profileTab,adminTab));
        tabs.forEach(tab -> {
            tab.setInactiveColor(inactive);
            tab.addActionListener(e -> {
              inactiveTab.setColor(inactiveTab.getInactiveColor());
              tab.setColor(active);
              inactiveTab = tab;
            });
          });

         //you can customize font, inactive acitve
    }

    private void createLogoutTab(){
      logoutTab = new Button(tabPoint.x + 990, tabPoint.y, "         Logout", MasterGUI.purple);
      logoutTab.setTab();
      sidebar.add(logoutTab);

      ActionListener logoutAction = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
          dispose();
          panel.removeAll();
          new LoginGUI();
        }
      };

      logoutTab.addActionListener(confirmDialogAction(logoutAction, "Do you really want to logout?"));
    }
    public static void confirmDialog(String prompt) {
      confirmDialog(null, null, prompt);
    }
  
    
    public static void confirmDialog(ActionListener action, String prompt) {
      confirmDialog(action, null, prompt);
    }
    public static void confirmDialog(ActionListener action, ActionListener failAction, String prompt) {
      JDialog confirmDialog = new JDialog(frame, "Confirm action");
      frame.setEnabled(false);
  
      /**
       * Prevent app frame from staying disabled when user closes dialog
       */
      confirmDialog.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          frame.setEnabled(true);
        }
      });
  
      Label logoutlabel = new Label(30, 30, prompt, MasterGUI.purple);
      Button yes = new Button(30, 60, "Yes", MasterGUI.black_gray);
      Button no = new Button(140, 60, "No", MasterGUI.black_gray);
      //no.setDark(false);
  
      JPanel logoutp = new JPanel();
      logoutp.setLayout(null);
      logoutp.setBackground(MasterGUI.babyblue);
      logoutp.add(logoutlabel);
      if (action != null) {
        logoutp.add(no);
      } else {
        yes.setLocation(85, 60);
        yes.setText("OK");
      }
      logoutp.add(yes);
  
      confirmDialog.add(logoutp);
      confirmDialog.setSize(300, 160);
      confirmDialog.setResizable(false);
      confirmDialog.setVisible(true);
      confirmDialog.setLocationRelativeTo(null);
  
      setComponentStyles(logoutp, "light");
  
      ActionListener closeDialog = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
          frame.setEnabled(true);
          confirmDialog.dispose();
        }
      };
      yes.addActionListener(action);
      if (failAction != null)
        no.addActionListener(failAction);
      yes.addActionListener(closeDialog);
      no.addActionListener(closeDialog);
    }
    public static ActionListener confirmDialogAction(ActionListener action, String prompt) {
      return new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          confirmDialog(action, prompt);
        }
      };
    }
  
    public static void switchPanel(JPanel newPanel) {
        frame.remove(currentPanel);
        frame.add(newPanel);
        currentPanel = newPanel;
        
        frame.repaint();
      }

    public static ActionListener switchPanelAction(JPanel newPanel) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(newPanel);
          }
        };
    }
    private void createAdminPanel(){
        AdminView adminView = new AdminView(frame,user);
        adminTab = new Button(tabPoint.x+130, tabPoint.y, "         Admin", adminView);
        adminTab.setTab();
        if(user.getAdmin())sidebar.add(adminTab);
    }
   
    public static void main(String[] args) throws Exception {
        System.out.println("Starting MainGUI");
        //User guest = DatabaseAPI.getUser("Admin");
        User guest = DatabaseAPI.getUser("Phil");
        MainGUI system = new MainGUI(guest);
        system.setLocationRelativeTo(null);
    }
}
