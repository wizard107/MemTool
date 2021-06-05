package Views.Components;
import java.awt.Font;

import javax.swing.*;

public class Label extends JLabel{
  /*
  previously isUnset
  */
  private boolean isEditable = false;
  private boolean isHeader = false;
  public Label() {
    super();
    setFont(new Font("Arial", Font.PLAIN, 15));
    setSize(250, 25);
  }
  public Label(int x, int y, String text) {
    super(text);
    setFont(new Font("Arial", Font.PLAIN, 15));
    setBounds(x, y, 250, 25);
  }
  public void setEditable(){
    isEditable = true;
  }
  public boolean getEditable(){
    return isEditable;
  }

  
}
