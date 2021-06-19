package Views.Components;
import javax.swing.*;

import Views.MainGUI;
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
    private ActionListener switchPanelAction;
    private boolean filled = true;
    private boolean isTab = false;
    private Color inactiveColor;
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
    //only for tabbuttons
    public Button(int x, int y, String text, JPanel switchTo) {
      super(text);
      defaultSettings();
      switchPanelAction = MainGUI.switchPanelAction(switchTo);
      addActionListener(switchPanelAction);
      setLocation(x, y);
      setTab();
      filled = true;
      }

    public Button(int x, int y, String text, Color color, int w_new, int h_new) {
        super(text);
        defaultSettings(w_new, h_new);
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
      public Button(int x, int y, String text, int w, int h) {
        super(text);
        defaultSettings(w,h);
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
    public void defaultSettings(int width_new, int height_new){
      setFont(MasterGUI.basicFont);
      setCursor(cursor);
      setSize(width_new, height_new);
      setBorderPainted(false);
      addMouseListener(this);
  }

    public void setColor(Color color) {
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
    //for tab buttons, to make them the same colour as sidebar
    public void setTab() {
      if (getTab()) return;
      setSize(130, 50);
      setHorizontalAlignment(SwingConstants.LEADING);
      setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
      //setFont(this.getFont().deriveFont(13f));
      setFont(MasterGUI.poppinsFont);
      setForeground(Color.WHITE);
      setColor(MasterGUI.purple.darker());
      setFocusPainted(false);
      setContentAreaFilled(true);
      setIconTextGap(15);
      isTab = true;
    }

    public boolean getTab(){
      return isTab;
    }

    public void setInactiveColor(Color color) {
      inactiveColor = color;
    }
    public Color getInactiveColor(){
      return inactiveColor;
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
