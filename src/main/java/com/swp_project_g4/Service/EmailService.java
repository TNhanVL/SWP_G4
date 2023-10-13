/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Service;

import javax.mail.Authenticator;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author TTNhan
 */
/**
 *
 * @author Thanh Duong
 */
public class EmailService {
//    thanhduongjnguyen@gmail.com
//    cpqrdilisnasjxoe

    final static String from = "yojihangroup@gmail.com";
    final static String password = "drmoubkcmogfmrlu";

    public static int mailTo(String obj, String title, String type, String content) {
        try {
            final String to = obj;

            Properties props = new Properties();

            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

//        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP HOST
//        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

//             Create Authentcator
            Authenticator auth = new Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(from, password); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                }

            };

            // Phien lam viec
            Session session = Session.getInstance(props, auth);

            MimeMessage msg = new MimeMessage(session);

            //Type 
            msg.addHeader("Content-type", "text;charset=UTF-8");
            //From
            msg.setFrom(from);
            //To
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parseHeader(to, false));
            //Title
            msg.setSubject(title, "UTF-8");
            //Time
            msg.setSentDate(new Date());

            //Set reply
            //msg.setReplyto(InternetAddress.parseHeader(from, false))
            msg.setText(content, "UTF-8", type);

            Transport.send(msg);

        } catch (Exception ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public static void sendWelcomMail(String obj, String courseURL) {
        String welcome = "<div>\n"
                + "        <p><b>Dear Dylanruan1210,</b></p>\n"
                + "        <p>I would like to express my gratitude for your participation in the course. Thank you for enrolling and\n"
                + "            showing interest in furthering your studies.</p>\n"
                + "        <p>I am eagerly anticipating the opportunity to collaborate with you and assist you in preparing for your\n"
                + "            academic pursuits. Together, we will strive towards achieving your goals.</p>\n"
                + "        <p>If you happen to know anyone who shares an interest in the subject matter of this course, I kindly request\n"
                + "            that you forward this email to them. It would be greatly appreciated if you could help spread the word and\n"
                + "            extend this valuable learning opportunity to others.</p>\n"
                + "        <p>Once again, I extend a warm welcome to you as your lecturer in the course. I am confident that our\n"
                + "            collaboration will be productive and mutually beneficial. I eagerly anticipate working harmoniously with you\n"
                + "            in the near future.</p>\n"
                + "        <p>Thank you once again for your commitment and dedication.</p>\n"
                + "        <p><b>Best regards,</b></p>\n"
                + "    </div>\n"
                + "\n"
                + "    <div>\n"
                + "        <p><b>Dear Dylanruan1210,</b></p>\n"
                + "        <p>Congratulations! You’ve successfully completed <b>Java Basic</b>.</p>\n"
                + "        <p><b>Best regards,</b></p>\n"
                + "    </div>\n"
                + "    <p><button style=\"padding: 10px;color:#fff;background-color: #048eff;\"><a style=\"color:#fff;\" href=\"" + courseURL + "\">View course</a></button></p>";

        mailTo(obj, "[Yojihan] Welcome to Java Basic!", "html", welcome);

    }

    public static void sendChangePassword(String obj, String url) {
        String changePassContent = "<p>Dear <b>Dylann11233</b></p><br><p>We noticed that you are trying to update your password. If that's really you, please <a href=\"" + url + "\">click here</a> to change your password.</p><p><b>Best regards,</b> </p>";

        mailTo(obj, "[Yojihan] Change your password!", "html", changePassContent);

    }

    public static void sendCompletecourse(String obj, String cerURL) {
        System.out.println(cerURL);
        String complete = "<div>\n"
                + "        <p><b>Dear Dylanruan1210,</b></p>\n"
                + "        <p>Congratulations! You’ve successfully completed <b>Java Basic</b>.</p>\n"
                + "        <p><b>Best regards,</b></p>\n"
                + "    </div>"
                + "    <p><button style=\"padding: 10px;color:#fff;background-color: #048eff;\"><a style=\"color:#fff;\" href=\"" + cerURL + "\">View Certificate</a></button></p>";

        mailTo(obj, "[Yojihan] Congratulations, Your Certificate is Ready!", "html", complete);

    }

    public static void main(String[] args) {
//        mailTo("thanhduongjnguyen@gmail.com", "You have ...", "html", "<a href=\"https://www.facebook.com\">View </a>)");

//        mailTo("thanhduongjnguyen@gmail.com", "You have ...", "html", welcome);
//        mailTo("thanhduongjnguyen@gmail.com", "You have ...", "html", complete);
//        mailTo("thanhduongjnguyen@gmail.com", "You have ...", "html", changePassContent);
//        sendWelcomMail("thanhduongjnguyen@gmail.com", "http://127.0.0.1:5500/courseInfo.html");
//            sendChangePassword("thanhduongjnguyen@gmail.com", "http://127.0.0.1:5500/changPassword.html");
        sendCompletecourse("nhan12341184@gmail.com", "http://127.0.0.1:5500/courseInfo.html");
    }

}
