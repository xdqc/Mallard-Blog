package application;

import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Login extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        String dbPath = getServletContext().getRealPath("/WEB-INF/mysql.properties");
        new DbConnector(dbPath);
    }


}
