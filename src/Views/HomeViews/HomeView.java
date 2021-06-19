package Views.HomeViews;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Views.Components.Label;
import Views.Components.Panel;

public class HomeView extends Panel{
    private static JFrame frame;
    private static JPanel superPanel;
    private static JPanel subPanel;
    public HomeView(JFrame frame){
        super(frame);
        HomeView.frame = frame;
        superPanel = new Panel();
        subPanel = new Panel();
        subPanel.setBounds(0,200,frame.getWidth(), frame.getHeight() * 2-150);
        //superPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        superPanel.setBounds(0,200, frame.getWidth(), frame.getHeight());
        subPanel.setBackground(Color.blue);
        superPanel.setBackground(Color.BLACK);
        Label x = new Label(100, 200, "hallo", Color.RED);
        Label y = new Label(100, 100, "hallo2", Color.RED);
        
        superPanel.add(subPanel);
        superPanel.add(y);
        add(superPanel);
        add(x);
        
    }
}
