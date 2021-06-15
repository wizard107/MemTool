package Views;
import javax.swing.*;
import java.awt.*;


public class MainGUI extends MasterGUI{
    private static JFrame frame;

    MainGUI(){
        frame = this;
        setTitle("MemTool");
        setSize(1000,500);
        
        remove(panel); // removes LoginGUI panel content as panel from mastergui still saved it
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
