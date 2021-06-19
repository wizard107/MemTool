package Views.HomeViews;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Views.Components.Label;
import Views.Components.Panel;

public class HomeView extends JPanel{
    private static JFrame frame;
    private static JPanel superPanel;
    private static JPanel subPanel;
    public HomeView(JFrame frame){
        //super(frame);
        //HomeView.frame = frame;
        this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        //this.setBackground(new Color(255,250,250));
        this.setLayout(null);
      
        //this.setBackground(Color.BLACK);
        this.setLayout(null);
        superPanel = new Panel();
        subPanel = new Panel();
        subPanel.setBounds(0,200,frame.getWidth(), frame.getHeight() * 2-150);
        //superPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        superPanel.setBounds(0,200, frame.getWidth(), frame.getHeight());
        //superPanel.setPreferredSize(null);
        subPanel.setBackground(Color.blue);
        superPanel.setBackground(Color.BLACK);
        //superPanel.setPreferredSize(new Dimension(100,200));
        Label x = new Label(100, 200, "hallo", Color.RED);
        Label y = new Label(100, 100, "hallo2", Color.RED);
        
        superPanel.add(subPanel);
        superPanel.add(y);
        add(superPanel);
        add(x);
        
    }
}
