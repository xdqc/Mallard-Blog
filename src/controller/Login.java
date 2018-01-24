package controller;

import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        String dbPath = getServletContext().getRealPath("/WEB-INF/mysql.properties");
        new DbConnector(dbPath);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isLogout = req.getParameter("logout") !=null && req.getParameter("logout").equals("1");
        if (isLogout){
            // TODo CLEAR SESSION

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
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserRecord user = DbConnector.getUserByUsername(username);
        if (user==null){
            req.getRequestDispatcher("login?failed=1").forward(req, resp);
            return;
        } else if (!authenticationPassed(user, password)) {
            req.getRequestDispatcher("login?failed=1").forward(req, resp);
            return;
        } else {
            req.getSession().setAttribute("loggedInUser", user);
            req.getRequestDispatcher("home-page").forward(req, resp);
        }

    }

    private boolean authenticationPassed(UserRecord user, String password) {
        //TODO implement auth

        return user.getPassword().equals(password);
    }
}
