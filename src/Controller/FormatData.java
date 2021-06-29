package Controller;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatData {

    /**
     * Resize an image to a specified factor and retain its resolution
     *
     * @param img        - Source ImageIcon to be resized
     * @param proportion - Resize factor to resize image
     * @return Copy of img in resized dimensions
     */
    public static ImageIcon resizeImageIcon(ImageIcon img, float proportion) {
        int width = img.getIconWidth();
        int height = img.getIconHeight();

        width = Math.round(width * proportion);
        height = Math.round(height * proportion);

        Image image = img.getImage();
        Image newimg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    /**
     * Check if a string is blank or not
     *
     * @param string - the string to be checked
     * @return Boolean whether string is blanked or not
     */
    public static boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }

    /**
     * Format a given string that contains tags into an array of string
     * only containing the tags by using regular expression
     *
     * @param tagText
     * @return
     */
    public static String[] formatHashtags(String tagText) {
        String regex = "(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)";
        Pattern tagMatcher = Pattern.compile(regex);
        Matcher m = tagMatcher.matcher(tagText);
        String allTagsAppended = "";
        while(m.find())
        {
            String tag = m.group(1);
            allTagsAppended += tag;
        }

        String[] tags = allTagsAppended.split("\\#");
        return  tags;
    }

}
