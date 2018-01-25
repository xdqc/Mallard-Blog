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
        UserRecord user = getLoggedUserFromSession(req);
        if (user!=null){
            req.getRequestDispatcher("home-page").forward(req, resp);
            return;
        }


        boolean isLogin = req.getParameter("login") != null && req.getParameter("login").equals("1");
        if (isLogin){
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            UserRecord userRecord = DbConnector.getUserByUsername(username);
            if (userRecord==null){
                req.getRequestDispatcher("login?failed=1&login=0").forward(req, resp);
                return;
            } else if (!authenticationPassed(userRecord, password)) {
                req.getRequestDispatcher("login?failed=1&login=0").forward(req, resp);
                return;
            } else {
                // success
                req.getSession().setAttribute("loggedInUser", userRecord);
                req.getRequestDispatcher("home-page").forward(req, resp);
            }
            return;
        }

        boolean isLoggingIn = req.getParameter("login") !=null && req.getParameter("login").equals("0");
        if (isLoggingIn){
            req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
            return;
        }

        boolean isLogout = req.getParameter("logout") !=null && req.getParameter("logout").equals("1");
        if (isLogout){
            // CLEAR SESSION
            HttpSession session = req.getSession(false);
            session.invalidate();
            req.getRequestDispatcher("home-page").forward(req, resp);
            return;
        }

        boolean isGuest = req.getParameter("guest") != null && req.getParameter("guest").equals("1");
        if (isGuest){
            req.getRequestDispatcher("home-page").forward(req, resp);
            return;
        }

        boolean isLoginFailed = req.getParameter("failed") != null && req.getParameter("failed").equals("1");
        if (isLoginFailed){
            req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
            return;
        }



        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }

    private boolean authenticationPassed(UserRecord user, String password) {
        //TODO implement auth

        return user.getPassword().equals(password);
    }
}
