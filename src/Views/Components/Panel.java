package Views.Components;
import java.awt.Color;

import javax.swing.*;

import Views.*;

public class Panel extends JPanel{
    public Panel() {
        super();
        this.setLayout(null);
      }
    public Panel(JFrame frame) {
      this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
      this.setBackground(new Color(255,250,250));
      this.setLayout(null);
      }  
      public void updateBounds(JFrame frame) {
        this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
      }
}
