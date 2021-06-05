package Views.Components;
import javax.swing.*;

import Views.MasterGUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Cursor;

public class Button extends JButton implements MouseListener {
    private Color color;
    private int width = 100;
    private int height = 40;
    private boolean dark = true;
    private boolean filled = true;
    private Cursor cursor = new Cursor(Cursor.HAND_CURSOR);  
    public Button(int x, int y, String text, Color color) {
        super(text);
        defaultSettings();
        setColor(color);
        setLocation(x, y);
        setBackground(color);
        setDark(true);
        filled = true;
      }
    



    public Button(int x, int y, String text) {
        super(text);
        defaultSettings();
        setLocation(x, y);
        setBackground(new Color(55, 55, 70));
        setDark(true);
        setContentAreaFilled(false);
        filled = false;
      }
    
    public void defaultSettings(){
        setFont(MasterGUI.basicFont);
        setCursor(cursor);
        setSize(width, height);
        setBorderPainted(false);
        addMouseListener(this);
    }

    private void setColor(Color color) {
        this.setBackground(color);
        this.color = color;
    }

    public void setDark(boolean dark) {
        if (getDark() == dark)
          return;
        this.dark = dark;
        if (dark)
          setForeground(Color.WHITE);
        else
          setForeground(Color.BLACK);
      }

    public Boolean getDark() {
      return this.dark;
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    //if (this.filled) {
    //  this.setBackground(color.darker());
    //}
    }

  
    /** 
     * if mouse is no longer placed above entity
     * @param e mousebutton is exited
     */
    @Override
    public void mouseExited(MouseEvent e) {
        //if (this.filled) {
        // this.setBackground(color);
        //}
    }

    
    /** 
     * mouse clicked on entity to provoke action
     * @param e mousebutton is pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
    // this.setBackground(color);
    }

    
    /** 
     * after mouse button is released
     * @param e mousebutton is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    
    /** 
     * after mouse button has been pressed and released
     * @param e mousebutton is clicked
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
