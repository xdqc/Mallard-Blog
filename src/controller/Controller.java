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
import java.util.function.Consumer;

public abstract class Controller extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        String dbPath = getServletContext().getRealPath("/WEB-INF/mysql.properties");
        new DbConnector(dbPath);
    }

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    void cleanAllParameters(HttpServletRequest req){
        Enumeration names = req.getParameterNames();
        while(names.hasMoreElements()){
            req.removeAttribute((String)names.nextElement());
        }
    }


    /**
     * @return Logged User, or null if not logged in
     */
    UserRecord getLoggedUserFromSession(HttpServletRequest req) {
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


    /**
     * Redirect a HttpRequest to target
     * @param paramFrom parameter in request
     * @param target a web page or servlet
     * @return Successfully redirected or not
     */
    boolean redirectTo(String paramFrom, String target, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] paramsFrom = paramFrom.split("=");
        if (req.getParameter(paramsFrom[0]) !=null && req.getParameter(paramsFrom[0]).equals(paramsFrom[1])){
            cleanAllParameters(req);
            req.setAttribute("contextPath", req.getContextPath());
            req.getRequestDispatcher(target).forward(req, resp);
            return true;
        }
        return false;
    }

    /**
     * Redirect a HttpRequest to target and do something
     * @param action do something before redirection
     * @return Successfully redirected or not
     */
    boolean redirectTo(Consumer<HttpSession> action, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] paramsFrom = "logout=1".split("=");
        if (req.getParameter(paramsFrom[0]) !=null && req.getParameter(paramsFrom[0]).equals(paramsFrom[1])){

            action.accept(req.getSession(false));

            cleanAllParameters(req);
            req.setAttribute("contextPath", req.getContextPath());
            req.getRequestDispatcher("home-page").forward(req, resp);
            return true;
        }
        return false;
    }

    /**
     * Redirect a HttpRequest to target if requested by a logged user
     * @param target a web page or servlet
     * @return Successfully redirected or not
     */
    boolean loggedUserRedirectTo(String target, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (getLoggedUserFromSession(req) != null){
            cleanAllParameters(req);
            req.setAttribute("contextPath", req.getContextPath());
            req.getRequestDispatcher(target).forward(req, resp);
            return true;
        }
        return false;
    }

}
