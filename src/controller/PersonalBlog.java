package controller;


import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import org.json.simple.JSONObject;
import utililties.Blog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PersonalBlog extends Controller {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String articleId = req.getParameter("articleId");
        String userId = req.getParameter("userId");

        UserRecord loggedUser = getLoggedUserFromSession(req);

        // Get user to be shown by the parameter passed by home-page
        UserRecord user = (articleId == null || articleId.isEmpty())
                ? DbConnector.getUserByUserId(userId)
                : DbConnector.getAuthorByArticleId(articleId);

        // Decide which user's blog going to display
        if (user == null && loggedUser == null) {
            String errorMsg = userId.isEmpty()
                    ? "Please log in to check your personal blog."
                    : "Too bad! We cannot find that user.";
            req.getRequestDispatcher("error?userId=" + userId + "&errorMsg=" + errorMsg).forward(req, resp);
            return;
        } else {
            if (user == null) {
                user = loggedUser;
            }
            HttpSession session = req.getSession(false);
            session.setAttribute("browsingUser", user);
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
        req.setAttribute("current_username", user.getUserName());


        userId = String.valueOf(user.getId());

        List<Blog> blogs = DbConnector.getBlogsByUserId(userId);
        req.setAttribute("blogs", blogs);

        req.getRequestDispatcher("/personal_blog.jsp").forward(req, resp);
    }


    /* This is for ajax*/

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //doGet(req, resp);

        String articleId = req.getParameter("comment");
        if (articleId != null) {
            Blog blog = DbConnector.getBlogByArticleId(articleId);
            ajaxCommentsHandler(req, resp, blog);
            return;
        }

        articleId = req.getParameter("content");
        if (articleId != null) {
            Blog blog = DbConnector.getBlogByArticleId(articleId);
            ajaxContentHandler(blog, req, resp);
            return;
        }
    }


    private void ajaxContentHandler(Blog blog, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<Integer, String> contentMap = new HashMap<>();
        contentMap.put(blog.getArticle().getId(), blog.getArticle().getContent());

        JSONObject json = new JSONObject(contentMap);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        json.writeJSONString(resp.getWriter());
    }


    private void ajaxCommentsHandler(HttpServletRequest req, HttpServletResponse resp, Blog blog) throws IOException {
        List<CommentRecord> comments = new LinkedList<>();

        blog.getCommentTree().traverse(blog.getCommentTree(), comments);

        Map<Integer, String> commentMap = new TreeMap<>();
        for (CommentRecord comment : comments) {
            if (comment.getCommenter() != null) {
                commentMap.put(comment.getId(), comment.getContent());
            }
        }

        JSONObject json = new JSONObject(commentMap);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        json.writeJSONString(resp.getWriter());
    }

}
