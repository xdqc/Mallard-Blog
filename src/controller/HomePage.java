package controller;

import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import utililties.Blog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePage extends Controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRecord user = getLoggedUserFromSession(req);

        //get all articles sort by like number
        //List<Blog> blogList = DbConnector.getHotBlogsSort();
        List<Blog> blogList = null;


        //when the user is a visitor, the user can visit the homepage
        if (user==null){
            cleanAllParameters(req);
            req.getRequestDispatcher("/home_page.jsp").forward(req, resp);
            return;
        }


        // user will not edit article on homepage
        req.setAttribute("blogs", blogList);
        req.setAttribute("browsingUser", null);
        req.getRequestDispatcher("/home_page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("loadHomepageProfile")!=null){
            //set the user information
            UserRecord user = getLoggedUserFromSession(req);
            Date dob = user.getDob();
            long diff = Calendar.getInstance().getTime().getTime() - dob.getTime();
            long age = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) / 365;

            req.setAttribute("post_number", DbConnector.getPostNumber(String.valueOf(user.getId())));
            req.setAttribute("follower_number", DbConnector.getFollowerNumber(String.valueOf(user.getId())));
            req.setAttribute("current_username", user.getUserName());
            req.setAttribute("fname", user.getFName());
            req.setAttribute("lname", user.getLName());
            req.setAttribute("email", user.getEmail());
            req.setAttribute("gender", user.getGender());
            req.setAttribute("country", user.getCountry());
            req.setAttribute("description", user.getDescription());
            req.setAttribute("age", age);
            req.setAttribute("userId", user.getId());

            req.getRequestDispatcher("WEB-INF/_home_page_profile.jsp").forward(req, resp);
            cleanAllParameters(req);
            return;
        }

        doGet(req, resp);
    }

}
