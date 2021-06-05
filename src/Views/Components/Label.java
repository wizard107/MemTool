package Views.Components;
import java.awt.Font;
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
    setFont(MasterGUI.basicFont);
    setSize(250, 25);
  }
  public Label(int x, int y, String text) {
    super(text);
    setFont(MasterGUI.basicFont);
    setBounds(x, y, 250, 25);
  }
  public Label(String text, Component cmp) {
    super(text);
    setFont(MasterGUI.basicFont);
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
  public void setHeader(){
    isHeader = true;
    isEditable = true;
    setSize(500,40);
    setFont(getFont().deriveFont(Font.BOLD, 30f));
  }
  public boolean getHeader(){
    return isHeader;
  }
  
}
