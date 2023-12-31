/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Service;

import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Learner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
@Service
public class EmailService {
    final static String from = "yojihangroup@gmail.com";
    final static String password = "drmoubkcmogfmrlu";

    @Async
    public void sendResetPasswordEmail(int learnerID, String resetToken) {
        Learner learner = LearnerDAO.getUser(learnerID);

        var emailContent = "<div>\n"
                + "        <p><b>Dear " + learner.getFirstName() + " " + learner.getLastName() + ",</b></p>\n"
                + "        <p>We have received a request to reset your password for your account<b>" + "</b>.</p>\n"
                + "        <p>To ensure the security of your account, please follow the instructions below to reset your password.</p>\n"
                + "        <p>Click on the following link to access the password reset page</p>\n"
                + "        <p><button style=\"padding: 10px;color:#fff;background-color: #048eff;\"><a href=\"http://localhost:8080/resetPassword?token=" + resetToken + "\" style=\"color: white; decoration: none;\">Reset password</a></p>\n"
                + "        <p>If you did not initiate this request or believe it to be in error, please contact our support team immediately</p>\n"
                + "        <p>Please note that for security reasons, this link will expire within the next 5 minus"
                + " and only possible to reset password on the same device made this request</p>\n"
                + "        <p>Best regards,</p>\n"
                + "        <p>The Yojihan Team</p>\n"
                + "</div>";

        mailTo(learner.getEmail(), "Reset password for  " + learner.getUsername(), "html", emailContent);
    }

    private String generateEnrollEmailContent(Learner learner, Course course) {
        return "<div>\n"
                + "        <p><b>Dear " + learner.getFirstName() + " " + learner.getLastName() + ",</b></p>\n"
                + "        <p>Congratulations! You have successfully enrolled in the course <b>" + course.getName() + "</b>.</p>\n"
                + "        <p>We hope you enjoy the course and learn a lot. If you have any questions, please do not hesitate to contact us.</p>\n"
                + "        <p>To access the course, please click here:</p>\n"
                + "        <p><button style=\"padding: 10px;color:#fff;background-color: #048eff;\"><a href=\"http://localhost:8080/course/" + course.getID() + "\" style=\"color: white; decoration: none;\">View course</a></p>\n"
                + "        <p>Best regards,</p>\n"
                + "        <p>The Yojihan Team</p>\n"
                + "</div>";
    }

    private String generateChangePasswordEmailContent(Learner learner) {
        return "<div>\n"
                + "        <p><b>Dear " + learner.getFirstName() + " " + learner.getLastName() + ",</b></p>\n"
                + "        <p>You have successfully changed your password. Please keep your password safe and confidential.</p>\n"
                + "        <p>If you have any questions, please do not hesitate to contact us.</p>\n"
                + "        <p>Best regards,</p>\n"
                + "        <p>The Yojihan Team</p>\n"
                + "</div>";

    }

    private String generateCompleteCourseEmailContent(Learner learner, Course course) {
        return "<div>\n"
                + "        <p><b>Dear " + learner.getFirstName() + " " + learner.getLastName() + ",</b></p>\n"
                + "        <p>Congratulations! You have successfully completed the course <b>" + course.getName() + "</b>.</p>\n"
                + "        <p>We hope you enjoyed the course and learned a lot. Thank you for being a part of the Yojihan community.</p>\n"
                + "        <p>To access your certificate, please click on the following link:</p>\n"
                + "        <p><button style=\"padding: 10px;color:#fff;background-color: #048eff;\"><a href=\"http://localhost:8080/certificate/" + learner.getID() + "/" + course.getID() + "\" style=\"color: white; decoration: none;\">View Certification</a></p>\n"
                + "        <p>Best regards,</p>\n"
                + "        <p>The Yojihan Team</p>\n"
                + "</div>";
    }

    @Async
    public void sendEnrollEmail(Learner learner, Course course) {
        var emailContent = generateEnrollEmailContent(learner, course);
        mailTo(learner.getEmail(), "[Yojihan] Successfully Enrolled in " + course.getName(), "html", emailContent);
    }

    @Async
    public void sendChangePasswordEmail(Learner learner) {
        var emailContent = generateChangePasswordEmailContent(learner);
        mailTo(learner.getEmail(), "[Yojihan] Password Changed Successfully!", "html", emailContent);
    }

    @Async
    public void sendCompleteCourseEmail(Learner learner, Course course) {
        var emailContent = generateCompleteCourseEmailContent(learner, course);
        mailTo(learner.getEmail(), "[Yojihan] Congratulations, Your Certificate is Ready!", "html", emailContent);
    }

    // Handle the event of successful course registration
    @Async
    public void handleEnrollCourseSuccess(Learner learner, Course course) {
        sendEnrollEmail(learner, course);
    }

    // Handle the event of successful password change
    @Async
    public void handleChangePasswordSuccess(Learner learner) {
        sendChangePasswordEmail(learner);
    }

    // Handle the event of completed course
    @Async
    public void handleCompleteCourse(Learner learner, Course course, String cerURL) {
        sendCompleteCourseEmail(learner, course);
    }

    public int mailTo(String obj, String title, String type, String content) {
        try {
            final String to = obj;
            Properties props = new Properties();

            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // Create Authentcator
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
            msg.setText(content, "UTF-8", type);

            Transport.send(msg);
        } catch (Exception ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public static void main(String[] args) {
        //Test thử
//        // Khởi tạo đối tượng EmailService
//        EmailService emailService = new EmailService();
//        // Tìm kiếm learner có ID = 1
//        Learner learner = LearnerDAO.getUser(1);
//        // Tìm kiếm khóa học mà learner đã đăng ký
//        Course course = CourseDAO.getCourse(2);
//        // Gửi email đăng ký thành công khóa học
//        emailService.sendEnrollEmail(learner, course);
//        // Gửi email đổi mật khẩu thành công
//        emailService.sendChangePasswordEmail(learner);
//        //Gửi email hoàn thành khóa học được cấp chứng chỉ
//        emailService.sendCompleteCourseEmail(learner, course);
//        sendEmail(3, 5, "enroll");
//        emailService.mailTo("diemhuongnt.vl@gmail.com", "Certificate", "html", complete);

    }
}
