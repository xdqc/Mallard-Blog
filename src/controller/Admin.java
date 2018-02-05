package controller;

import ORM.tables.records.ArticleRecord;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import utililties.Passwords;
import utililties.Tuple;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static controller.Login.hashingPassword;

public class Admin extends Controller {

    // Storing the send password link, after ONE time use, remove the link
    private static Map<String, Tuple<String, byte[]>> activeLink = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * For user using reset pw link
         */
        if (req.getParameter("resetPasswordFor") != null) {

            String encodeUserId = req.getParameter("resetPasswordFor");

            // the link is invalid
            if (!activeLink.containsKey(encodeUserId)) {
                String errorMsg = "Your reset password link is invalid.";
                req.getRequestDispatcher("error?errorMsg=" + errorMsg).forward(req, resp);
                return;
            }

            String userId = decodeUserIdFromRandomizedLink(encodeUserId);

            if (userId.isEmpty()){
                String errorMsg = "Your reset password link is invalid.";
                req.getRequestDispatcher("error?errorMsg=" + errorMsg).forward(req, resp);
                return;
            }

            UserRecord user = DbConnector.getUserByUserId(userId);

            if (user == null) {
                String errorMsg = "User not exist is our blog system.";
                req.getRequestDispatcher("error?userId=" + userId + "&errorMsg=" + errorMsg).forward(req, resp);
                return;
            }


            req.setAttribute("user", user);
            req.getRequestDispatcher("WEB-INF/reset_password.jsp").forward(req, resp);
            return;
        }


        /**
         * Only show admin page to administrator
         */
        UserRecord adminUser = getLoggedUserFromSession(req);
        if (adminUser!=null && adminUser.getSystemRole()==0){
            List<Tuple<?, ?>> allRecords = DbConnector.getAllRecords();

            List<UserRecord> userResults = allRecords.stream()
                    .filter(r -> r.Val1 instanceof UserRecord)
                    .map(r -> (UserRecord) r.Val1)
                    .collect(Collectors.toList());

            List<Tuple<ArticleRecord, UserRecord>> articleResults = allRecords.stream()
                    .filter(r -> r.Val1 instanceof ArticleRecord)
                    .map(r -> (Tuple<ArticleRecord, UserRecord>) r)
                    .collect(Collectors.toList());

            List<Tuple<CommentRecord, UserRecord>> commentResults = allRecords.stream()
                    .filter(r -> r.Val1 instanceof CommentRecord)
                    .map(r -> (Tuple<CommentRecord, UserRecord>) r)
                    .collect(Collectors.toList());

            req.setAttribute("userResults", userResults);
            req.setAttribute("articleResults", articleResults);
            req.setAttribute("commentResults", commentResults);
            req.getRequestDispatcher("WEB-INF/admin.jsp").forward(req, resp);
            return;
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*
         * send email with reset password link
         */
        if (req.getParameter("sendEmail") != null) {
            String userId = req.getParameter("userId");
            String username = req.getParameter("username");
            String userEmail = req.getParameter("sendEmail");

            userEmail = "mallard.blog@gmail.com";


            // reads SMTP server setting from web.xml file
            ServletContext context = getServletContext();
            String host = context.getInitParameter("host");
            String port = context.getInitParameter("port");
            String user = context.getInitParameter("user");
            String pass = context.getInitParameter("pass");
            String server = context.getInitParameter("server");

            //GENERATE A LINK
            String encodePart = encodeResetPasswordLink(userId);

            String link = server + "/admin?resetPasswordFor=" + encodePart;


            // Get system properties
            Properties props = System.getProperties();

            // Setup mail server
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", user);
            props.put("mail.smtp.password", pass);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port", port);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            // Get the default Session object.
            Session session = Session.getInstance(props);
            session.setDebug(true);


            // Recipient's email ID needs to be mentioned.
            String to = userEmail;
            // Sender's email ID needs to be mentioned

            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(user));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: header field
                message.setSubject("Reset password link! - from Mallard-Blog.co.nz");

                // Now set the actual message
                message.setText("Please check your reset password link below:\n\n" + link
                        + "\n\nBe ware, you can only user this link one time.");

                // Send message
                Transport transport = session.getTransport("smtp");
                transport.connect(host, user, pass);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

                // Feed back response to ajax
                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write("A reset password email for " + username + " has been send to " + userEmail);

            } catch (MessagingException mex) {
                mex.printStackTrace();
            }

            return;
        }

        /**
         * Process the reset password form
         */
        if (req.getParameter("passwordReset") != null) {
            // clear current session
            req.getSession(false).invalidate();

            String userId = req.getParameter("passwordReset");
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            UserRecord user = DbConnector.getUserByUserId(userId);
            String enCodedPassword = hashingPassword(password, user);

            DbConnector.resetPasswordByUserID(enCodedPassword, userId);

            cleanAllParameters(req);
            req.getRequestDispatcher("login?login=0").forward(req, resp);
            return;
        }


        /**
         * Delete user's own account done by himself
         */
        if (req.getParameter("deleteOwnAccount")!=null){
            String userId = req.getParameter("deleteOwnAccount");

            DbConnector.deleteUserById(userId);

            req.getSession(false).invalidate();
            cleanAllParameters(req);

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("userId= "+userId+" is deleted.");
            return;
        }

        /**
         * Delete user action
         */
        if (req.getParameter("deleteUser")!=null){
            String userId = req.getParameter("deleteUser");

            DbConnector.deleteUserById(userId);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("userId= "+userId+" is deleted.");
            return;
        }

        /**
         * Recover user action
         */
        if (req.getParameter("recoverUser")!=null){
            String userId = req.getParameter("recoverUser");

            DbConnector.recoverUserById(userId);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("userId= "+userId+" is recovered.");
            return;
        }


        /**
         * Show article action
         */
        if (req.getParameter("showArticle")!=null){
            String articleID = req.getParameter("showArticle");

            DbConnector.showArticleById(articleID);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("articleId= "+articleID+" is shown.");
            return;
        }

        /**
         * Hide article action
         */
        if (req.getParameter("hideArticle")!=null){
            String articleId = req.getParameter("hideArticle");

            DbConnector.hideArticleById(articleId);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("articleId= "+articleId+" is hidden.");
            return;
        }


        /**
         * Show comment action
         */
        if (req.getParameter("showComment")!=null){
            String commentId = req.getParameter("showComment");

            DbConnector.showCommentById(commentId);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("commentId= "+commentId+" is shown.");
            return;
        }

        /**
         * Hide comment action
         */
        if (req.getParameter("hideComment")!=null){
            String commentId = req.getParameter("hideComment");

            DbConnector.hideCommentById(commentId);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("commentId= "+commentId+" is hidden.");
            return;
        }



    }

    private String encodeResetPasswordLink(String userId) {
        // Append random to userId ,make the userId string longer, so that harder to break
        String randomizedUserId = String.valueOf(Integer.parseInt(userId) + Math.random());

        byte[] salt = Passwords.getNextSalt();
        byte[] hash = Passwords.hash(randomizedUserId.toCharArray(), salt);

        String encodedLinkPart = Passwords.base64Encode(hash);

        activeLink.put(encodedLinkPart, new Tuple<>(randomizedUserId, salt));

        return encodedLinkPart;
    }


    private String decodeUserIdFromRandomizedLink(String encodeUserId) {
        String plainUserId = "";

        byte[] hash = Passwords.base64Decode(encodeUserId);
        byte[] salt = activeLink.get(encodeUserId).Val2;
        String uncheckedUserId = activeLink.get(encodeUserId).Val1;

        if (Passwords.isExpectedPassword(uncheckedUserId.toCharArray(), salt, hash)){
            plainUserId = uncheckedUserId.substring(0, uncheckedUserId.indexOf('.'));
        }

        // after used, de-active the link
        activeLink.remove(encodeUserId);

        return plainUserId;
    }
}
