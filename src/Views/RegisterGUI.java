package Views;
import java.awt.Color;

import javax.swing.*;

public class RegisterGUI extends MasterGUI{
    public RegisterGUI(){
        setTitle("Registration Form");
        setSize(1000,500);
        panel.updateBounds(this);
        //panel.setBackground(new Color(55, 55, 70));
        setLocationRelativeTo(null);
        //setComponentStyles(panel,null);
    }
}