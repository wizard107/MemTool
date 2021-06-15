package Views.Components;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;

import Views.MasterGUI;

public class Label extends JLabel{
  /*
  previously isUnset
  */
  private boolean isEditable = false;
  private boolean isHeader = false;
  public Label() {
    super();
    setFont(MasterGUI.poppinsFont);
    setSize(250, 25);
  }
  public Label(int x, int y, String text, Color color) {
    super(text);
    setFont(MasterGUI.poppinsFont);
    setBounds(x, y, 250, 25);
    setForeground(color);
  }
  public Label(int x, int y, String text, int HeaderSize, Color color) {
    super(text);
    setFont(MasterGUI.poppinsFont);
    setHeader(HeaderSize);
    setBounds(x, y, 250, 25);
    if(color!=null) setForeground(color);
  }
  public Label(String text, Component cmp) {
    super(text);
    setFont(MasterGUI.poppinsFont);
    setBounds(cmp.getX() + cmp.getWidth() + 12, cmp.getY(), 250, cmp.getHeight());
  }

  public Label(ImageIcon image) {
    super(image);
  }
  public void setEditable(){
    isEditable = true;
  }
  public boolean getEditable(){
    return isEditable;
  }
  public void setHeader(int HeaderSize){
    isHeader = true;
    isEditable = true;
    setSize(550,60);
    switch(HeaderSize){
      case 0: setFont(getFont().deriveFont(Font.BOLD, 30f));break;
      case 1: setFont(getFont().deriveFont(Font.BOLD, 25f));break;
      case 2: setFont(getFont().deriveFont(Font.BOLD, 20f));break;
      case 3: setFont(getFont().deriveFont(Font.BOLD, 13f));break; 
      default: System.out.println("can't recognize headersize");break;
    }

  }
  public boolean getHeader(){
    return isHeader;
  }
  
}
