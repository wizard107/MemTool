package Views.Components;
import javax.swing.*;

public class Panel extends JPanel{
    public Panel() {
        super();
        this.setLayout(null);
      }
      public void updateBounds(JFrame frame) {
        this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
      }
}
