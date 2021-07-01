package Views.Components;

import Views.MasterGUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;
public class TextField extends JTextField{
    private final int WIDTH = 310;
    private final int HEIGHT = 40;
   
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
      public void defaultSettings(){
        setCaretColor(new Color(60, 60, 75));
        setBackground(new Color(240, 240, 245));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
      }

      public void setToStaticMode() {
        this.setBackground(MasterGUI.babyred);
        this.setForeground(MasterGUI.black);
        repaint();
        this.setEditable(false);
      }

      public void setToEditMode() {
          this.setBackground(MasterGUI.white);
          this.setForeground(MasterGUI.black);
          repaint();
          this.setEditable(true);
      }

      

    public void appendLabel(String name, JPanel panel) {
        Label label = new Label(this.getX(), this.getY() - 25, name, Color.black);
        panel.add(label);
    }
}
