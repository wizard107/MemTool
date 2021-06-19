package Views;
import javax.swing.*;

import Views.Components.Button;
import Views.HomeViews.HomeView;
import Views.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private static Button inactiveTab;
    public static HomeView homePanel;
    public static JPanel deckPanel;
    public static JPanel searchPanel;
    public static JPanel profilePanel;

    MainGUI(){
        frame = this;
        setTitle("MemTool");
        setSize(1200,700);
        remove(panel); // removes LoginGUI panel content as panel from mastergui still saved it
        
        tabPoint = new Point(0,0);
        homePanel = new HomeView(frame);
        deckPanel = new JPanel();
        searchPanel = new JPanel();
        profilePanel = new JPanel();
        currentPanel = homePanel;
        add(sidebar);
        add(homePanel);
        createSidebar();
        createSidebarTabs();
        setComponentStyles(panel, "light");
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createSidebar(){
        sidebar.setBackground(MasterGUI.purple.darker());
        sidebar.setBounds(0,0,this.getWidth(),50);
        sidebar.setLayout(null);
    }

    
    private void createSidebarTabs(){
        homeTab = new Button(tabPoint.x, tabPoint.y, "Home", homePanel);
        deckTab = new Button(tabPoint.x + 600, tabPoint.y, "Manage Decks", deckPanel);
        searchTab = new Button(tabPoint.x + 730, tabPoint.y, "         Search", searchPanel);
        profileTab = new Button(tabPoint.x + 860, tabPoint.y, "         Profile", profilePanel);
        //searchTab.setSize(65,50);
        //profileTab.setSize(65,50);
        sidebar.add(homeTab);
        sidebar.add(deckTab);
        sidebar.add(searchTab);
        sidebar.add(profileTab);
        Color active = MasterGUI.purple;
        Color inactive = MasterGUI.purple.darker();
        homeTab.setColor(active);
        inactiveTab = homeTab;
        List<Button> tabs = new ArrayList<>(
        Arrays.asList(homeTab, searchTab, deckTab, profileTab));
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

    public static void switchPanel(JPanel newPanel) {
        frame.remove(currentPanel);
        currentPanel = newPanel;
        frame.add(currentPanel);
        frame.repaint();
      }

    public static ActionListener switchPanelAction(JPanel newPanel) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(newPanel);
          }
        };
      }
    public static void main(String[] args) throws Exception {
        System.out.println("Starting MainGUI");
        MainGUI system = new MainGUI();
        system.setLocationRelativeTo(null);
    }
}
