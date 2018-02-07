package controller;

import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import utililties.Passwords;
import utililties.Tuple;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import static controller.Login.hashingPassword;

public class ResetPassword extends Controller{

    // Storing the send password link, after ONE time use, remove the link
    private static final Map<String, Tuple<String, byte[]>> activeLink = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * For user using reset pw link
         */
        if (req.getParameter("resetPassword") != null) {

            String encodeUserId = req.getParameter("resetPassword");

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
                String errorMsg = "User not exist in our blog system.";
                req.getRequestDispatcher("error?userId=" + userId + "&errorMsg=" + errorMsg).forward(req, resp);
                return;
            }


            req.setAttribute("user", user);
            req.getRequestDispatcher("WEB-INF/reset_password.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * send email with reset password link
         */
        if (req.getParameter("sendEmail") != null) {
            String userId = req.getParameter("userId");
            String username = req.getParameter("username");
            String userEmail = req.getParameter("sendEmail");

            // reads SMTP server setting from web.xml file
            ServletContext context = getServletContext();
            String host = context.getInitParameter("host");
            String port = context.getInitParameter("port");
            String user = context.getInitParameter("user");
            String pass = context.getInitParameter("pass");
            String server = context.getInitParameter("server");

            //GENERATE A LINK
            String encodePart = encodeResetPasswordLink(userId);

            String webServer = req.getRequestURL().substring(0, req.getRequestURL().lastIndexOf("/"));

            String link = webServer + "/reset-password?resetPassword=" + encodePart;


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
            // Sender's email ID needs to be mentioned

            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(user));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

                // Set Subject: header field
                message.setSubject("Reset password link! - from Mallard-Blog.co.nz");

                // Now set the actual message
                message.setText("Please check your reset password link below:\n\n" + link
                        + "\n\nBe ware, you can only use this link for one time.");

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
        }
    }


    /**
     *
     * @param userId
     * @return encoded b64 string
     */
    private String encodeResetPasswordLink(String userId) {

        String encodedLinkPart = " ";
        Pattern pattern = Pattern.compile("\\s");

        //Make sure there is no space in encoded b64
        while (pattern.matcher(encodedLinkPart).find()){
            // Append random number to userId ,make the encoded userId string almost impossible to break
            String randomizedUserId = String.valueOf(Integer.parseInt(userId) + Math.random());

            byte[] salt = Passwords.getNextSalt();
            byte[] hash = Passwords.hash(randomizedUserId.toCharArray(), salt);

            encodedLinkPart = Passwords.base64Encode(hash);

            activeLink.put(encodedLinkPart, new Tuple<>(randomizedUserId, salt));
        }
        return encodedLinkPart;
    }


    /**
     *
     * @param encodeUserId encoded b64 string
     * @return userId
     */
    private String decodeUserIdFromRandomizedLink(String encodeUserId) {
        String plainUserId = "";

        byte[] hash = Passwords.base64Decode(encodeUserId);
        byte[] salt = activeLink.get(encodeUserId).Val2;
        String uncheckedUserId = activeLink.get(encodeUserId).Val1;

        if (Passwords.isExpectedPassword(uncheckedUserId.toCharArray(), salt, hash)){
            //get the whole number part of the randomized userId
            plainUserId = uncheckedUserId.substring(0, uncheckedUserId.indexOf('.'));
        }

        // after used, de-active the link
        activeLink.remove(encodeUserId);

        return plainUserId;
    }
}
