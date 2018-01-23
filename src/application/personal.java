package application;


import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class personal extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        String dbPath = getServletContext().getRealPath("/WEB-INF/mysql.properties");
        new DbConnector(dbPath);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String articleId = req.getParameter("articleId");

        UserRecord user = DbConnector.getAuthorByArticleId(articleId);

        String fname = user.getFName();
        String lname = user.getLName();
        String email = user.getEmail();
        Date dob = user.getDob();
        String country = user.getCountry();
        String description = user.getDescription();
        long diff = Calendar.getInstance().getTime().getTime() - dob.getTime();
        long age = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) / 365;

        req.setAttribute("fname", fname);
        req.setAttribute("lname", lname);
        req.setAttribute("email", email);
        req.setAttribute("age", age);
        req.setAttribute("country", country);
        req.setAttribute("description", description);

        req.getRequestDispatcher("/personal_blog.jsp").forward(req, resp);
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
