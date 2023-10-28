/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Service;

import com.swp_project_g4.Database.CourseDAO;
import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Learner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TTNhan
 */

/**
 * @author Ko^ phai? dim~ huong?
 */
public class EmailService {
    final static String from = "yojihangroup@gmail.com";
    final static String password = "drmoubkcmogfmrlu";

    public static void sendResetPasswordEmail(int learnerID) {
        Learner learner = LearnerDAO.getUser(learnerID);

        var emailContent = "Legalize nuclear bombing";

        mailTo(learner.getEmail(), "Test reset password", "html", emailContent);
    }

    public static void sendEmail(int learnerID, int courseID, String type) {
        Learner learner = LearnerDAO.getUser(learnerID);
        Course course = CourseDAO.getCourse(courseID);

        // Generate email content
        String emailContent = "";
        switch (type) {
            case "enroll":
                emailContent = generateEnrollEmailContent(learner, course);
                break;
            case "change_password":
                emailContent = generateChangePasswordEmailContent(learner);
                break;
            case "certification":
                emailContent = generateCompleteCourseEmailContent(learner, course);
                break;

        }

        // Send email
        mailTo(learner.getEmail(), generateEmailSubject(type, course.getName()), "html", emailContent);
    }

    private static String generateEnrollEmailContent(Learner learner, Course course) {
        String content = "<div>\n"
                + "        <p><b>Dear " + learner.getFirstName() + " " + learner.getLastName() + ",</b></p>\n"
                + "        <p>Congratulations! You have successfully enrolled in the course <b>" + course.getName() + "</b>.</p>\n"
                + "        <p>We hope you enjoy the course and learn a lot. If you have any questions, please do not hesitate to contact us.</p>\n"
                + "        <p>To access the course, please click here:</p>\n"
                + "        <p><button style=\"padding: 10px;color:#fff;background-color: #048eff;\"><a href=\"http://localhost:8080/course/" + course.getID() + "\" style=\"color: white; decoration: none;\">View course</a></p>\n"
                + "        <p>Best regards,</p>\n"
                + "        <p>The Yojihan Team</p>\n"
                + "</div>";
        return content;
    }

    private static String generateChangePasswordEmailContent(Learner learner) {
        String content = "<div>\n"
                + "        <p><b>Dear " + learner.getFirstName() + " " + learner.getLastName() + ",</b></p>\n"
                + "        <p>You have successfully changed your password. Please keep your password safe and confidential.</p>\n"
                + "        <p>If you have any questions, please do not hesitate to contact us.</p>\n"
                + "        <p>Best regards,</p>\n"
                + "        <p>The Yojihan Team</p>\n"
                + "</div>";
        return content;
    }

    private static String generateCompleteCourseEmailContent(Learner learner, Course course) {
        String content = "<div>\n"
                + "        <p><b>Dear " + learner.getFirstName() + " " + learner.getLastName() + ",</b></p>\n"
                + "        <p>Congratulations! You have successfully completed the course <b>" + course.getName() + "</b>.</p>\n"
                + "        <p>We hope you enjoyed the course and learned a lot. Thank you for being a part of the Yojihan community.</p>\n"
                + "        <p>To access your certificate, please click on the following link:</p>\n"
                + "        <p><button style=\"padding: 10px;color:#fff;background-color: #048eff;\"><a href=\"http://localhost:8080/public/media/certificate/certificate_" + course.getID() + "_" + learner.getID() + ".pdf" + "\" style=\"color: white; decoration: none;\">View Certification</a></p>\n"
                + "        <p>Best regards,</p>\n"
                + "        <p>The Yojihan Team</p>\n"
                + "</div>";
        return content;
    }

    private static String generateEmailSubject(String type, String courseName) {
        String subject = "";
        switch (type) {
            case "enroll":
                subject = "[Yojihan] Successfully enrolled in the course " + courseName;
                break;
            case "change_password":
                subject = "[Yojihan] Successfully changed your password!";
                break;
            case "certification":
                subject = "[Yojihan] Congratulations, Your Certificate is Ready!";
                break;
            default:
                subject = "Notification from Yojihan!";
                break;
        }
        return subject;
    }

    //CODE CŨ
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

    //CODE CŨ
    public static void sendCompletecourse(Learner learner, Course course, String cerURL) {
        System.out.println(cerURL);
        String complete = "<div>\n"
                + "        <p><b>Dear " + learner.getFirstName() + " " + learner.getLastName() + ",</b></p>\n"
                + "        <p>Congratulations! You’ve successfully completed <b>" + course.getName() + "</b>.</p>\n"
                + "        <p><b>Best regards,</b></p>\n"
                + "    </div>"
                + "    <p><button style=\"padding: 10px;color:#fff;background-color: #048eff;\"><a style=\"color:#fff;\" href=\"" + cerURL + "\">View Certificate</a></button></p>";

        mailTo(learner.getEmail(), "[Yojihan] Congratulations, Your Certificate is Ready!", "html", complete);
    }

    public static void main(String[] args) {
        sendEmail(3,5,"enroll");
//        mailTo("diemhuongnt.vl@gmail.com", "Certificate", "html", complete);

    }

}
