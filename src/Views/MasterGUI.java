package Views;
import javax.swing.*;
import javax.swing.text.JTextComponent;

import Views.Components.Button;
import Views.Components.Label;
import Views.Components.Panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;

public class MasterGUI extends JFrame{
    public static Font basicFont = new Font("Arial", Font.PLAIN, 15);
    protected static Panel panel = new Panel();
    public static void placeFieldLabel(Component comp, String name, JPanel panel) {
        Label label = new Label(comp.getX(), comp.getY() - 25, name);
        panel.add(label);
      }
    public MasterGUI() {
        //setIconImage(favicon.getImage());
        setResizable(false);
        setLayout(null);
        panel.setBackground(new Color(55, 55, 70));
        panel.setLayout(null);
        add(panel);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
              | UnsupportedLookAndFeelException ex) {
          }

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
              //DatabaseAPI.closeDatabase();
              System.exit(0);
            }
        });
      }
    public static void setComponentStyles(JPanel panel, String colorMode) {
        Color foreground;
        Color background;
        if (colorMode == "dark" || colorMode == null) {
          foreground = Color.WHITE;
          background = new Color(60, 60, 75);
        } else if (colorMode == "light") {
          foreground = new Color(60, 60, 75);;
          background = new Color(240, 240, 245);
        } else {
          throw new IllegalArgumentException("Invalid color mode.");
        }
    
        for (Component c : panel.getComponents()) {
          if (c instanceof JLabel) {
            if (c instanceof Label && !((Label) c).getEditable()) {
              c.setFont(new Font("Arial", Font.PLAIN, 15));
              c.setForeground(foreground);
            }
          }
          if (c instanceof JTextField) {
            c.setFont(new Font("Arial", Font.PLAIN, 15));
            c.setBackground(background);
            c.setForeground(foreground);
            ((JTextComponent) c).setCaretColor(foreground);
          }
          if (c instanceof JButton) {
            c.setFont(new Font("Arial", Font.PLAIN, 15));
            if (((Button) c).getDark()) {
              ((AbstractButton) c).setForeground(Color.WHITE);
            }
            ((AbstractButton) c).setFocusPainted(false);
            ((AbstractButton) c).setContentAreaFilled(true);
            ((AbstractButton) c).setMargin(new Insets(5, 5, 3, 3));
          }
          if (c instanceof JPasswordField) {
            c.setFont(new Font("Consolas", Font.PLAIN, 15));
            ((JTextComponent) c).setCaretColor(foreground);
            ((JTextComponent) c).setBackground(background);
            ((JTextComponent) c).setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
          }
    
        }
    }
}
