package controller;

import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUp extends Controller {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("error?errorMsg=You think you are smart again?").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserRecord user = getLoggedUserFromSession(req);

        if (user != null) {
            req.getRequestDispatcher("edit_profile.jsp").forward(req, resp);
            return;
        }


        boolean isSigningUp = req.getParameter("signUp") != null && req.getParameter("signUp").equals("0");
        if (isSigningUp){
            req.getRequestDispatcher("WEB-INF/sign_up.jsp").forward(req, resp);
            return;
        }

        boolean isSignedIn = req.getParameter("signUp") != null && req.getParameter("signUp").equals("1");
        if (isSignedIn){
            //TODO insert info to db


            req.getRequestDispatcher("upload_profile.jsp").forward(req, resp);
            return;
        }




    }
}
