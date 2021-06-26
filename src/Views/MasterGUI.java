package Views;
import javax.swing.*;
import javax.swing.text.JTextComponent;

import Views.Components.Button;
import Views.Components.Label;
import Views.Components.Panel;
import java.awt.FontFormatException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MasterGUI extends JFrame{
    public static Font basicFont = new Font("Arial", Font.PLAIN, 15);
    public static Font underline = new Font("Arial", Font.PLAIN, 15);
    public static Font basicFontFat = new Font("Arial", Font.BOLD, 15);
    public static Color black_gray = new Color(55,55,70);
    public static Color purple = new Color(128,131,201);
    public static Color babyblue = new Color(223,240,247); 
    public static Color babyred = new Color(255,203,199); 
    public static Color yellow = new Color(252,208,44);
    public static Color white = new Color(240, 240, 245);
    public static Color beige = new Color(245, 245, 245);
    public static Color green = new Color(96, 165, 97);
    public static Color red = new Color(227, 74, 100);
    public static Color blue = new Color(66, 103, 178);
    public static Color black = new Color(40,40,40);
    public static Font poppinsFont;
    public static Font poppinsFontBig;
    public static Font bodyFont;
    private static File fileRoot = new File(System.getProperty("user.dir"));
    private static String imagesRoot = "/assets/images/";
    public static ImageIcon learnPNG = new ImageIcon(fileRoot + imagesRoot + "learn.png");
    public static ImageIcon stopPNG = new ImageIcon(fileRoot + imagesRoot + "stop.png");
    public static ImageIcon pcPNG = new ImageIcon(fileRoot + imagesRoot + "admin.png");
    protected static Panel panel = new Panel();
    public static void placeFieldLabel(Component comp, String name, JPanel panel) {
        Label label = new Label(comp.getX(), comp.getY() - 25, name, null);
        panel.add(label);
      }
    public MasterGUI() {
        //setIconImage(favicon.getImage());
        setResizable(false);
        setLayout(null);
        //panel.setBackground(new Color(55, 55, 70));
        panel.setBackground(new Color(255, 255, 255));
        panel.setLayout(null);
        add(panel);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
              | UnsupportedLookAndFeelException ex) {
          }
        
          try {
            /*bodyFont = Font
                .createFont(Font.TRUETYPE_FONT, new File(fileRoot + "/assets/fonts/UniversLTStd.otf"))
                .deriveFont(13f);
      */
            poppinsFont = Font
                .createFont(Font.TRUETYPE_FONT, new File(fileRoot + "/assets/fonts/Poppins-Regular.ttf"))
                .deriveFont(16f);
            poppinsFontBig = Font.createFont(Font.TRUETYPE_FONT, new File(fileRoot + "/assets/fonts/Poppins-Regular.ttf"))
            .deriveFont(20f);
          } catch (IOException | FontFormatException e) {
            System.out.println("MasterGUI" + e.getMessage());
            bodyFont = basicFont; // if font asset import failed, fall back to Arial
            poppinsFont = bodyFont;
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
          foreground = new Color(60, 60, 75);
          //foreground = new Color(128,131,201);
          background = new Color(240, 240, 245);
        }
          else if (colorMode == "purple"){
            foreground = new Color(128,131,201);
            background = new Color(240, 240, 245);
          }
        else {
          throw new IllegalArgumentException("Invalid color mode.");
        }
    
        for (Component c : panel.getComponents()) {
          if (c instanceof JLabel) {
            if (c instanceof Label && !((Label) c).getEditable()) {
              //c.setFont(new Font("Arial", Font.PLAIN, 15));
              c.setFont(poppinsFont);
              c.setForeground(foreground);
            }
          }
          if (c instanceof JTextField) {
            //c.setFont(new Font("Arial", Font.PLAIN, 15));
            c.setFont(poppinsFont);
            c.setBackground(background);
            c.setForeground(foreground);
            ((JTextComponent) c).setCaretColor(foreground);
          }
          if (c instanceof JButton) {
            c.setFont(poppinsFont);
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
