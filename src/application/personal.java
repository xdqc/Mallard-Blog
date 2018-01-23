package application;


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
import java.util.List;
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
        String userId = req.getParameter("userId");

        UserRecord user = (articleId == null || articleId.isEmpty())
                ? DbConnector.getUserByUserId(userId)
                : DbConnector.getAuthorByArticleId(articleId);

        if (user==null){
            String errorMsg = "Too bad! We cannot find that user.";
            req.setAttribute("errorMsg", errorMsg);
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        Date dob = user.getDob();
        long diff = Calendar.getInstance().getTime().getTime() - dob.getTime();
        long age = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) / 365;

        req.setAttribute("fname", user.getFName());
        req.setAttribute("lname", user.getLName());
        req.setAttribute("email", user.getEmail());
        req.setAttribute("gender", user.getGender());
        req.setAttribute("country", user.getCountry());
        req.setAttribute("description", user.getDescription());
        req.setAttribute("age", age);


        userId = String.valueOf(user.getId());
        List<ArticleRecord> articles = DbConnector.getArticlesByUserId(userId);
        req.setAttribute("articles", articles);

        req.getRequestDispatcher("/personal_blog.jsp").forward(req, resp);
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
