package controller;

import ORM.tables.User;
import ORM.tables.records.ArticleRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import utililties.Blog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePage extends Controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRecord user = getLoggedUserFromSession(req);

        //get all articles sort by like number
        List<ArticleRecord> articles = DbConnector.getHotArticlesSort();
        req.setAttribute("articles", articles);

        //pack article and it's author and comments in to one object called "blog"
        List<Blog> blogList = new LinkedList<>();
        for (ArticleRecord article : articles) {
            blogList.add(new Blog(article));
        }
        req.setAttribute("blogs", blogList);



        //when the user is a visitor, the user can visit the homepage
        if (user==null){
            cleanAllParameters(req);
            req.getRequestDispatcher("/home_page.jsp").forward(req, resp);
            return;
        }
        //set the user information
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


        req.getRequestDispatcher("/home_page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }

}
