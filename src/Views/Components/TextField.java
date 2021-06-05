package Views.Components;

import java.awt.Color;

import javax.swing.*;

public class TextField extends JTextField{
    public TextField(int x, int y, String title) {
        super(title);
        this.setBounds(x, y, WIDTH, HEIGHT);
        defaultSettings();
      }
    
      public TextField(String title) {
        super(title);
        defaultSettings();
        setSize(WIDTH, HEIGHT);
      }
    
      public TextField(int x, int y) {
        super();
        this.setBounds(x, y, WIDTH, HEIGHT);
        defaultSettings();
      }
      //previously setdefaultstyle
      public void defaultSettings(){
        setCaretColor(new Color(60, 60, 75));
        setBackground(new Color(240, 240, 245));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
      }
    
}
