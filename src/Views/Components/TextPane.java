package Views.Components;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JTextPane;

import Views.MasterGUI;

public class TextPane extends JTextPane{
    public TextPane(int x, int y, int w, int h) {
        super();
        this.setBounds(x, y, w, h);
        defaultSettings();
      }
      public void defaultSettings(){
        setCaretColor(new Color(60, 60, 75));
        setBackground(new Color(240, 240, 245));
        setFont(MasterGUI.poppinsFont.deriveFont(20f));
        setForeground(MasterGUI.black_gray);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
      }
}
