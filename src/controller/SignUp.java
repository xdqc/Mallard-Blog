package controller;

import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

import static controller.Login.hashingPassword;

public class SignUp extends Controller {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("error?errorMsg=You think you are smart again?").forward(req, resp);
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        if (loggedUserRedirectTo("edit_profile.jsp", req, resp))
            return;


        if (redirectTo("signUp=0", "WEB-INF/sign_up.jsp", req, resp))
            return;


        /**
         * Handle new user sign-up form processing
         */
        boolean isSignedIn = req.getParameter("signUp") != null && req.getParameter("signUp").equals("1");
        if (isSignedIn) {
            UserRecord user = new UserRecord();
            String systemRole = "1";
            Timestamp currentTimes = new Timestamp(Calendar.getInstance().getTime().getTime());
            String isValid = "1";

            user.setFName(req.getParameter("fname"));
            user.setLName(req.getParameter("lname"));
            user.setGender(Integer.valueOf(req.getParameter("gender")));
            user.setDob(java.sql.Date.valueOf(req.getParameter("dob")));
            user.setSystemRole(Integer.valueOf(systemRole));
            user.setCreateTime(currentTimes);
            user.setEmail(req.getParameter("email"));
            user.setUserName(req.getParameter("userName"));

            String encodedPassword = hashingPassword(req.getParameter("password"), req.getParameter("userName"));
            user.setPassword(encodedPassword);

            user.setAddress(req.getParameter("address"));
            user.setCity(req.getParameter("city"));
            user.setState(req.getParameter("state"));
            user.setCountry(req.getParameter("country"));
            user.setDescription(req.getParameter("description"));
            user.setIsvalid(Byte.valueOf(isValid));

            System.out.println(user);


            cleanAllParameters(req);
            DbConnector.insertNewUser(user);

            int newUserId = DbConnector.getNewlySignedUser(user);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(newUserId);

            return;
        }


        if (req.getParameter("chooseAvatar") != null) {
            String newUserId = req.getParameter("newUserId");

            req.setAttribute("newUserId", newUserId);
            req.getRequestDispatcher("WEB-INF/_choose_avatar.jsp").forward(req, resp);

        }

        //todo change attatchment db to setup avatar for that new user
        if (req.getParameter("setupAvatarFor") != null) {
            String newUserId = req.getParameter("setupAvatarFor");
            //TODO PROCESS THE AVATAR FORM DATA

            return;
        }


        if (req.getParameter("editProfile") != null) {
            String userId = req.getParameter("editProfile");
            editProfile(req, resp);
            return;
        }

    }


    private void editProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserRecord user = getLoggedUserFromSession(req);
        if (user==null)
            return;

        user.setFName(req.getParameter("fname"));
        user.setLName(req.getParameter("lname"));
        user.setGender(Integer.valueOf(req.getParameter("gender")));
        user.setDob(java.sql.Date.valueOf(req.getParameter("dob")));
        user.setAddress(req.getParameter("address"));
        user.setCity(req.getParameter("city"));
        user.setState(req.getParameter("state"));
        user.setCountry(req.getParameter("country"));
        user.setDescription(req.getParameter("description"));


        System.out.println(user);

        String msg = DbConnector.updateUserProfile(user) ? "success" : "error";
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(msg);
    }





}
