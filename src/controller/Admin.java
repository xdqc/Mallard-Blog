package controller;

import ORM.tables.records.ArticleRecord;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import utililties.Tuple;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Admin extends Controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Tuple<?,?>> allRecords = DbConnector.getAllRecords();

        List<UserRecord> userResults = allRecords.stream()
                .filter(r -> r.Val1 instanceof UserRecord)
                .map(r -> (UserRecord)r.Val1)
                .collect(Collectors.toList());

        List<Tuple<ArticleRecord, UserRecord>> articleResults = allRecords.stream()
                .filter(r -> r.Val1 instanceof ArticleRecord)
                .map(r -> (Tuple<ArticleRecord, UserRecord>)r)
                .collect(Collectors.toList());

        List<Tuple<CommentRecord, UserRecord>> commentResults = allRecords.stream()
                .filter(r -> r.Val1 instanceof CommentRecord)
                .map(r -> (Tuple<CommentRecord, UserRecord>)r)
                .collect(Collectors.toList());

        req.setAttribute("userResults", userResults);
        req.setAttribute("articleResults", articleResults);
        req.setAttribute("commentResults", commentResults);
        req.getRequestDispatcher("WEB-INF/admin.jsp").forward(req, resp);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("sendEmail")!=null){
            String userId = req.getParameter("userId");
            String username = req.getParameter("username");
            String userEmail = req.getParameter("sendEmail");

            userEmail = "qd16@students.waikato.ac.nz";

            //TODO GENERATE A LINK
            String link = "http://localhost:8080/admin";


            // Recipient's email ID needs to be mentioned.
            String to = userEmail;

            // Sender's email ID needs to be mentioned
            String from = "webmaster@mallard_blog.com";

            // Assuming you are sending email from localhost
            String host = "localhost";

            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.setProperty("mail.smtp.host", host);

            // Get the default Session object.
            Session session = Session.getDefaultInstance(properties);


            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: header field
                message.setSubject("Reset password link! - from Mallard-Blog.co.nz");

                // Now set the actual message

                message.setText("Please check your reset password link below:\n\n" + link);

                // Send message
                Transport.send(message);

                // Feed back response to ajax
                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write("A reset password email for " +username+ " has been send to "+userEmail);

            } catch (MessagingException mex) {
                mex.printStackTrace();
            }

        }

    }
}
