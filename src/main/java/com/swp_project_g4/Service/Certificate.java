/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp_project_g4.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.swp_project_g4.Database.CourseDAO;
import com.swp_project_g4.Database.InstructorDAO;
import com.swp_project_g4.Database.OrganizationDAO;
import com.swp_project_g4.Database.LearnerDAO;
import com.swp_project_g4.Model.Course;
import com.swp_project_g4.Model.Organization;
import com.swp_project_g4.Model.User;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author TTNhan
 */
public class Certificate {

    private static String srcPath = "src/main/webapp/public";

    static void addText(String text, String fontName, BaseColor baseColor, float size, float px, float py, PdfContentByte contentByte, Document document) {
        try {
            String fontPath = srcPath + "/assets/font/" + fontName;

            // Load the custom TTF font
            BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font customFont = new Font(baseFont, size, Font.NORMAL, baseColor);

            // Calculate the center coordinates
            float pageWidth = document.getPageSize().getWidth();
            float textWidth = customFont.getBaseFont().getWidthPoint(text, customFont.getSize());
            float x = (pageWidth - textWidth) / 2 + px;      // Center text
            float y = py;      //y position

            // Add content to the document
            contentByte.beginText();
            contentByte.setFontAndSize(baseFont, customFont.getSize());
            contentByte.setColorFill(customFont.getColor());
            contentByte.setTextMatrix(x, y);
            contentByte.showText(text);
            contentByte.endText();
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(Certificate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void addUserName(String UserName, PdfContentByte contentByte, Document document) {
        String fontName = "GreatVibes-Regular.ttf";
        BaseColor baseColor = new BaseColor(182, 140, 39);
        float size = 55;
        float px = 0;
        float py = 310;
        addText(UserName, fontName, baseColor, size, px, py, contentByte, document);
    }

    static void addCourseName(String CourseName, PdfContentByte contentByte, Document document) {
        String fontName = "PlayfairDisplaySC-Regular.ttf";
        BaseColor baseColor = new BaseColor(0, 0, 0);
        float size = 30;
        float px = 0;
        float py = 205;
        addText(CourseName, fontName, baseColor, size, px, py, contentByte, document);
    }

    static void addInstructorName(String InstructorName, PdfContentByte contentByte, Document document) {
        String fontName = "Garet-Book.ttf";
        BaseColor baseColor = new BaseColor(182, 140, 39);
        float size = 15.5f;
        float px = 155;
        float py = 110;
        addText(InstructorName, fontName, baseColor, size, px, py, contentByte, document);
    }

    static void addDate(PdfContentByte contentByte, Document document) {
        String fontName = "Garet-Book.ttf";
        BaseColor baseColor = new BaseColor(182, 140, 39);
        float size = 15.5f;
        float px = -155;
        float py = 110;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");

        addText(sdf.format(date), fontName, baseColor, size, px, py, contentByte, document);
    }

    static void addOrganizationName(String OrganizationName, PdfContentByte contentByte, Document document) {
        String fontName = "Garet-Book.ttf";
        BaseColor baseColor = new BaseColor(182, 140, 39);
        float size = 15.5f;
        float px = -155;
        float py = 135;
        addText(OrganizationName, fontName, baseColor, size, px, py, contentByte, document);
    }

    public static void createCertificate(String certificateName, int userID, int courseID, HttpServletRequest request) {
        
        srcPath = request.getSession().getServletContext().getRealPath("/") + "../../main/webapp/public";
        
        User user = LearnerDAO.getUser(userID);
        Course course = CourseDAO.getCourse(courseID);
        String imagePath = srcPath + "/assets/imgs/certificate/Yojihan_Certificate.png"; // Provide the path to your image file
        
        String outputFilename = srcPath + "/media/certificate/" + certificateName;

        try {
            // Initialize the document and writer
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFilename));
            document.open();

            // Load the background image
            Image backgroundImage = Image.getInstance(imagePath);

            // Set the size and position of the image to cover the whole page
            backgroundImage.scaleAbsolute(document.getPageSize());
            backgroundImage.setAbsolutePosition(0, 0);

            // Add the image to the document
            PdfContentByte contentByte = writer.getDirectContentUnder();
            contentByte.addImage(backgroundImage);

            // Add Info into the certificate
            addUserName(user.getFirstName() + " " + user.getLastName(), contentByte, document);
            addCourseName(course.getName(), contentByte, document);
            User instructor = InstructorDAO.getInstructor(course.getInstructorID());
            if (instructor != null) {
                addInstructorName(instructor.getFirstName() + " " + instructor.getLastName(), contentByte, document);
            }

            addDate(contentByte, document);

            Organization organization = OrganizationDAO.getOrganization(course.getOrganizationID());
            addOrganizationName(organization.getName(), contentByte, document);

            document.close();

            System.out.println("Certificate generated successfully!");

        } catch (DocumentException | IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
//        createCertificate("a.pdf", 2, 4);
    }
}
