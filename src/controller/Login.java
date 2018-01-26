package controller;

import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Login extends Controller {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("error?errorMsg=You think you are smart?").forward(req, resp);
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (redirectTo("logout=1", HttpSession::invalidate, "home-page", req, resp))
            return;

        if (loggedUserRedirectTo("home-page", req, resp)) {
            return;
        }

        if (redirectTo("guest=1", "home-page", req, resp))
            return;

        if (redirectTo("failed=1", "WEB-INF/login.jsp", req, resp))
            return;

        if (redirectTo("login=0", "WEB-INF/login.jsp", req, resp)) {
            return;
        }


        boolean isLogin = req.getParameter("login") != null && req.getParameter("login").equals("1");
        if (isLogin){
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            UserRecord userRecord = DbConnector.getUserByUsername(username);
            if (userRecord==null){
                cleanAllParameters(req);
                req.getRequestDispatcher("login?failed=1&login=0").forward(req, resp);
                return;
            } else if (!authenticationPassed(userRecord, password)) {
                cleanAllParameters(req);
                req.getRequestDispatcher("login?failed=1&login=0").forward(req, resp);
                return;
            } else {
                // success
                req.getSession().setAttribute("loggedInUser", userRecord);
                req.getRequestDispatcher("home-page").forward(req, resp);
            }
            return;
        }




        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }



    private boolean authenticationPassed(UserRecord user, String password) {
        //TODO implement auth

        return user.getPassword().equals(password);
    }
}
