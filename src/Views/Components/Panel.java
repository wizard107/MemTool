package Views.Components;
import java.awt.Color;

import javax.swing.*;

import Views.*;

public class Panel extends JPanel{

    public boolean rounded;

    public Panel() {
        super();
        this.setLayout(null);
      }
    public Panel(JFrame frame) {
      this.setBounds(0, +50, frame.getWidth(), frame.getHeight()-50);
      this.setBackground(new Color(255,250,250));
      //this.setBackground(MasterGUI.babyred);
      this.setLayout(null);
      }  
      public void updateBounds(JFrame frame) {
        this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
      }

    /**
     * Set panel to be rounded or not. Rounds corners of panel.
     * @param value - decides whether panel is round.
     */
    public void setRounded(boolean value){
        if(rounded != value){
            setOpaque(!value);
            rounded = value;
            repaint();
        }
    }
}
