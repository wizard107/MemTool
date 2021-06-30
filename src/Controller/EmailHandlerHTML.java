package Controller;

import Model.*;

import java.time.format.DateTimeFormatter;

/**
 * The Class EmailHandlerHTML is used for creating the email Layout
 * @author ZuHyunLee97
 */
public class EmailHandlerHTML {
    /**
     * Sets up the dynamical HTML layout for the emails
     * @param event relevant event
     * @param subject is the subject of mail
     * @return html layout for the mail
     */
    public static String setupText(String RegistrationCode) {
        return  "<style>" +
                "@import url('https://fonts.googleapis.com/css2?family=Roboto&display=swap');" +
                "</style>" +
                "<body>\n" +
                "    <div class=\"es-wrapper-color\">\n" +
                "        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td class=\"esd-email-paddings\" valign=\"top\">\n" +
                "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content esd-header-popover\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" esd-custom-block-id=\"7681\" align=\"center\">\n" +
                "                                        <table class=\"es-header-body\" style=\"background-color: #8884cc;\" width=\"550\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#74cfb7\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p15t es-p15b es-p15r es-p15l\" align=\"left\">\n" +
                "                                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"es-m-p0r es-m-p20b esd-container-frame\" width=\"328\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text es-m-txt-c es-p5\" align=\"left\">\n" +
                "                                                                                        <h1 style=\"color: #ffffff; line-height: 100%; font-family: roboto, 'helvetica neue', helvetica, arial, sans-serif;\">Confirm your Registration<br></h1>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                        <table cellspacing=\"0\" cellpadding=\"0\" align=\"right\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr class=\"es-hidden\">\n" +
                "                                                                    <td class=\"es-m-p20b esd-container-frame\" esd-custom-block-id=\"7704\" width=\"172\" align=\"left\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td>\n" +
                "                                                                                        <table cellspacing=\"0\" cellpadding=\"0\" align=\"right\">\n" +
                "                                                                                            <tbody>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td align=\"center\" class=\"esd-empty-container\" style=\"display: none;\"></td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                            </tbody>\n" +
                "                                                                                        </table>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" align=\"center\">\n" +
                "                                        <table class=\"es-content-body\" width=\"550\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p20t es-p35r es-p35l\" esd-custom-block-id=\"7685\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"480\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-image es-p40t es-p25b es-p35r es-p35l\" align=\"center\" style=\"font-size: 0px; font-family: roboto, 'helvetica neue', helvetica, arial, sans-serif;\"><a target=\"_blank\" href=\"<ahref=&quot;https://icon-library.net/icon/add-reminder-icon-2.html&quot;title=&quot;AddReminderIcon#348522&quot;\"><imgsrc=&quot;https://icon-library.net//images/add-reminder-icon/add-reminder-icon-2.jpg&quot;width=&quot;350&quot;/></a>\"><img top=200px src=\"https://www.pngkey.com/png/full/442-4421724_handshake-icon.png\" alt style=\"display: block;\" width=\"120\"></a></td>\n" +
                "                                                                                </tr>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text\" align=\"center\">\n" +
                "                                                                                        <h2 sytle=\"front family: roboto, 'helvetica neue', helvetica, arial, sans-serif;\">" +"Welcome!"+ "</h2>   \n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text es-p15t es-p10b es-p40l\" align=\"left\">\n" +
                "                                                                                        <p style=\"front family: roboto, 'helvetica neue', helvetica, arial, sans-serif;\"><strong></strong></p>\n" +
                "                                                                                        <p style=\"front family: roboto, 'helvetica neue', helvetica, arial, sans-serif;\"><strong></br>Thank you for joining the MemTool Community!</strong></p>\n" +
                "                                                                                        <p style=\"front family: roboto, 'helvetica neue', helvetica, arial, sans-serif;\"><strong>MemTool is a memorization Tool for users who want a more efficient, organized, fun learning experience.</strong></p>\n" +
                "                                                                                        <p style=\"front family: roboto, 'helvetica neue', helvetica, arial, sans-serif;\"><strong>We are really excited to have you on board. First, you need to confirm your registration by using your personal registrationcode below in the program! </strong></p>\n" +
                "                                                                                        <p style=\"front family: roboto, 'helvetica neue', helvetica, arial, sans-serif;\"><strong>Your Registrationcode: " + RegistrationCode + "</strong></p>\n" +
                "                                                                                        <p style=\"front family: roboto, 'helvetica neue', helvetica, arial, sans-serif;\"><strong>Our Best, </p> Your MemTool Team</strong></p>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-spacer es-p20t es-p15b\" align=\"center\" style=\"font-size:0\">\n" +
                "                                                                                        <table width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n" +
                "                                                                                            <tbody>\n" +
                "                                                                                                <tr>\n" +
                "                                                                                                    <td style=\"border-bottom: 3px solid #eeeeee; background: rgba(0, 0, 0, 0) none repeat scroll 0% 0%; height: 1px; width: 100%; margin: 0px;\"></td>\n" +
                "                                                                                                </tr>\n" +
                "                                                                                            </tbody>\n" +
                "                                                                                        </table>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p20t es-p40b es-p35r es-p35l\" esd-custom-block-id=\"7685\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"480\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text es-p20t\" align=\"right\">\n" +
                "                                                                                        <p style=\"front family: roboto, 'helvetica neue', helvetica, arial, sans-serif;\">This mail was created automatically by MemTool<br>Please do not reply<br><br>*for a proper view, please disable darkmode</p>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" esd-custom-block-id=\"7766\" align=\"center\">\n" +
                "                                        <table class=\"es-content-body\" style=\"border-bottom:10px solid #ffd42c;background-color: #ffffff;\" width=\"550\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffd42c\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table class=\"esd-footer-popover es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }
}