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
    /*public class LimitInputLength extends DocumentFilter {
      private int limit;

      public LimitInputLength(int limit) {
        if (limit <= 0) {
          throw new IllegalArgumentException("Limit can not be <= 0");
        }
        this.limit = limit;
      }

      @Override
      public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
          throws BadLocationException {
        int currentLength = fb.getDocument().getLength();
        int overLimit = (currentLength + text.length()) - limit - length;
        if (overLimit > 0) {
          text = text.substring(0, text.length() - overLimit);
        }
        if (text.length() > 0) {
          super.replace(fb, offset, length, text, attrs);
        }
      }
    }*/
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

      public void setToStaticMode() {
        this.setBackground(MasterGUI.black);
        this.setForeground(MasterGUI.white);
        repaint();
        this.setEditable(false);
      }

      public void setToEditMode() {
          this.setBackground(MasterGUI.white);
          this.setForeground(MasterGUI.black);
          repaint();
          this.setEditable(true);
      }

      /*public void setMaximumLength(int limit) {
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new LimitInputLength(limit));
      }*/

    public void appendLabel(String name, JPanel panel) {
        Label label = new Label(this.getX(), this.getY() - 25, name, Color.black);
        panel.add(label);
    }
}
