package controller;


import ORM.tables.records.ArticleRecord;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utililties.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
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

        /*Read more content*/
        String articleId = req.getParameter("comment");
        if (articleId != null) {
            //Blog blog = DbConnector.getBlogByArticleId(articleId);
            Comments comments = DbConnector.getCommentsByArticleId(articleId);
            Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> commentTree = comments.getCommentTree();
            ajaxCommentsHandler(commentTree, req, resp);
            return;
        }

        /*Load comments tree*/
        articleId = req.getParameter("content");
        if (articleId != null) {
            Blog blog = DbConnector.getBlogByArticleId(articleId);
            ajaxArticleContentHandler(blog, req, resp);
            return;
        }

        /*Create new article*/
        String articleStr = req.getParameter("newArticle");
        if (articleStr != null) {

            try {
                JSONObject article = (JSONObject) new JSONParser().parse(articleStr);
                ArticleRecord articleRecord = new ArticleRecord();
                articleRecord.setTitle((String) article.get("title"));
                articleRecord.setContent((String) article.get("content"));
                articleRecord.setAuthor(Integer.parseInt((String) article.get("authorId")));
                articleRecord.setCreateTime(new Timestamp((Long) article.get("createTime")));
                articleRecord.setValidTime(new Timestamp((Long) article.get("validTime")));

                System.out.println(articleRecord);
                String msg = "success";
                        //DbConnector.insertNewArticle(articleRecord) ? "success" : "error";
                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(msg);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return;
        }

    }


    private void ajaxArticleContentHandler(Blog blog, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<Integer, String> contentMap = new HashMap<>();
        contentMap.put(blog.getArticle().getId(), blog.getArticle().getContent());

        JSONObject json = new JSONObject(contentMap);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        json.writeJSONString(resp.getWriter());
    }


    private void ajaxCommentsHandler(Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> comments, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        JSONArray json = new JSONArray();
        putCommentTreeToJson(comments, json);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        json.writeJSONString(resp.getWriter());
    }


    /**
     * Create a JSON object to represent the comment tree recursively
     */
    private void putCommentTreeToJson(Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> tree, JSONArray json){
        for (Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> commentTree : tree.getChildren()) {
            CommentRecord comment = commentTree.getData().Val2;
            UserRecord commenter = commentTree.getData().Val1;
            UserRecord articleAuthor = commentTree.getData().Val3;
            if (comment.getShowHideStatus()==1 && commenter.getIsvalid()==1){

                JSONObject commentJson = new JSONObject();
                assert commenter != null;
                commentJson.put("articleAuthorId", articleAuthor.getId());
                commentJson.put("commenterId", commenter.getId());
                commentJson.put("commenter", commenter.getFName()+" "+commenter.getLName());
                commentJson.put("content", comment.getContent());
                commentJson.put("createTime", comment.getCreateTime().toLocalDateTime().toString());
                commentJson.put("editTime", comment.getEditTime()==null?null:comment.getEditTime().toLocalDateTime().toString());


                JSONArray commentArr = new JSONArray();
                JSONObject commentObj = new JSONObject();
                commentObj.put(comment.getId(), commentJson);

                commentArr.add(commentObj);
                json.add(commentArr);

                if (!commentTree.getChildren().isEmpty()){
                    putCommentTreeToJson(commentTree, commentArr);
                }
            }
        }

    }

}
