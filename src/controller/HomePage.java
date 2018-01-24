package controller;

import ORM.tables.records.ArticleRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePage extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        String dbPath = getServletContext().getRealPath("/WEB-INF/mysql.properties");
        new DbConnector(dbPath);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userId = req.getParameter("userId");
        UserRecord user = (userId == null || userId.equals(""))
                ? null
                :DbConnector.getUserByUserId(userId);

        //get all articles sort by like number
        List<ArticleRecord> articles = DbConnector.getHotArticlesSort();
        req.setAttribute("articles", articles);

        //when the user is a visitor, the user can visit the homepage
        if (user==null){
            cleanAllParameters(req);
            req.getRequestDispatcher("/home_page.jsp").forward(req, resp);
        }
        //set the user information
        Date dob = user.getDob();
        long diff = Calendar.getInstance().getTime().getTime() - dob.getTime();
        long age = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) / 365;

        req.setAttribute("post_number", DbConnector.getPostNumber(userId));
        req.setAttribute("follower_number", DbConnector.getFollowerNumber(userId));
        req.setAttribute("current_username", user.getUserName());
        req.setAttribute("fname", user.getFName());
        req.setAttribute("lname", user.getLName());
        req.setAttribute("email", user.getEmail());
        req.setAttribute("gender", user.getGender());
        req.setAttribute("country", user.getCountry());
        req.setAttribute("description", user.getDescription());
        req.setAttribute("age", age);


        req.getRequestDispatcher("/home_page.jsp").forward(req, resp);
    }

    private void cleanAllParameters(HttpServletRequest req){
        Enumeration names = req.getParameterNames();
        while(names.hasMoreElements()){
            req.removeAttribute((String)names.nextElement());
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
