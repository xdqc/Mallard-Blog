package controller;

import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

public abstract class Controller extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        String dbPath = getServletContext().getRealPath("/WEB-INF/mysql.properties");
        new DbConnector(dbPath);
    }

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;


    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    protected void cleanAllParameters(HttpServletRequest req){
        Enumeration names = req.getParameterNames();
        while(names.hasMoreElements()){
            req.removeAttribute((String)names.nextElement());
        }
    }

    protected UserRecord getLoggedUserFromSession(HttpServletRequest req) {
        // If current session does not exist, then it will NOT create a new session.
        HttpSession session = req.getSession(false);

        UserRecord user;
        if (session == null || session.getAttribute("loggedInUser") == null) {
            user = null;
        } else {
            user = (UserRecord) session.getAttribute("loggedInUser");
        }
        return user;
    }

}
