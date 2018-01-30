package controller;

import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class SignUp extends Controller {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("error?errorMsg=You think you are smart again?").forward(req, resp);
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        if (loggedUserRedirectTo("edit_profile.jsp", req, resp))
            return;


        if (redirectTo("signUp=0", "WEB-INF/sign_up.jsp", req, resp))
            return;


        boolean isSignedIn = req.getParameter("signUp") != null && req.getParameter("signUp").equals("1");
        if (isSignedIn) {
            UserRecord user = new UserRecord();
            String dob = req.getParameter("dob");

            user.setFName(req.getParameter("fname"));
            user.setLName(req.getParameter("lname"));
            user.setGender(Integer.valueOf(req.getParameter("gender")));
            user.setDob(java.sql.Date.valueOf(req.getParameter("dob")));
            user.setUserName(req.getParameter("userName"));
            user.setPassword(req.getParameter("password"));
            user.setAddress(req.getParameter("address"));
            user.setCity(req.getParameter("city"));
            user.setState(req.getParameter("state"));
            user.setCountry(req.getParameter("country"));
            user.setDescription(req.getParameter("description"));

            System.out.println(user);

        /*

             String msg = DbConnector.insertNewUser(user) ? "success" : "error" ;
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(msg);

            String msg = DbConnector.insertNewUser(user) ? "success" : "error" ;
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(msg);

        }*/
        }




/*
    private void editProfile(UserRecord user, HttpServletRequest req, HttpServletResponse resp) {


    }

    private String hashingPassword(String rawPassword){
        //TODO impliment hashing
        return rawPassword;
    }
}
*/
    }
}