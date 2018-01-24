package controller;


import ORM.tables.records.ArticleRecord;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import utililties.Blog;
import utililties.Tree;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PersonalBlog extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        String dbPath = getServletContext().getRealPath("/WEB-INF/mysql.properties");
        new DbConnector(dbPath);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String articleId = req.getParameter("articleId");
        String userId = req.getParameter("userId");

        // If current session does not exist, then it will NOT create a new session.
        HttpSession session = req.getSession(false);

        UserRecord loggedUser;
        if (session == null || session.getAttribute("loggedInUser") == null) {
            loggedUser = null;
        } else {
            loggedUser = (UserRecord) session.getAttribute("loggedInUser");
        }

        UserRecord user = (articleId == null || articleId.isEmpty())
                ? DbConnector.getUserByUserId(userId)
                : DbConnector.getAuthorByArticleId(articleId);

        if (user==null){
            String errorMsg = "Too bad! We cannot find that user.";
            req.getRequestDispatcher("error?userId=&errorMsg="+errorMsg).forward(req, resp);
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
        req.setAttribute("current_username", user.getUserName());


        userId = String.valueOf(user.getId());

//        Map<ArticleRecord, Tree<CommentRecord>> blogs = new TreeMap<>();
        List<ArticleRecord> articles = DbConnector.getArticlesByUserId(userId);
        List<Blog> blogList = new LinkedList<>();

        if (articles.size() > 0){
            // Load comments for each article
            for (ArticleRecord article : articles) {
//                Tree<CommentRecord> rootComment = new Tree<>(new CommentRecord());
//                List<CommentRecord> parentComments = DbConnector.getCommentsByArticleId(String.valueOf(article.getId()));
//                getChildComments(rootComment, parentComments);
//                blogs.put(article, rootComment);
                blogList.add(new Blog(article));
            }


            req.setAttribute("blogs", blogList);
//            req.setAttribute("rootComment", blogList.get(articles.get(0)));


        }

        req.setAttribute("articles", articles);

        req.getRequestDispatcher("/personal_blog.jsp").forward(req, resp);
    }

    /**
     * Add comments recursively to the comment tree
     * @param comment The parent
     * @param childrenComments The children
     */
    private void getChildComments(Tree<CommentRecord> comment, List<CommentRecord> childrenComments) {
        for (CommentRecord pComment : childrenComments) {
            Tree<CommentRecord> parent = new Tree<>(pComment);
            comment.addChild(parent);

            List<CommentRecord> children = DbConnector.getCommentsByParentCommentId(String.valueOf(pComment.getId()));
            if (children.size()>0){
                getChildComments(parent, children);
            }
        }
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
