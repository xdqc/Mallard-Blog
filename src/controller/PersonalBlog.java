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
            req.setAttribute("browsingUser", user);
        }


        /*request for user profile*/
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
        req.setAttribute("user", user);


        userId = String.valueOf(user.getId());

        List<Blog> blogs = DbConnector.getBlogsByUserId(userId);
        req.setAttribute("blogs", blogs);
        //Filter out comments that is hide (for showing the numComments badge)
        blogs.forEach(b -> b.getNumComments());


        req.getRequestDispatcher("/personal_blog.jsp").forward(req, resp);
    }



    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*load more articles*/
        if (req.getParameter("loadMoreArticles")!=null){
            int loadedNum = Integer.parseInt(req.getParameter("loadMoreArticles"));
            String loadAuthorId = req.getParameter("loadArticleAuthoredBy");

            //Query only valid article by valid user
            Blog loadBlog = DbConnector.getNextHotBlog(loadedNum);
            if (loadBlog != null){
                req.setAttribute("blog", loadBlog);
                req.setAttribute("id", String.valueOf(loadBlog.getArticle().getId()));
                req.getRequestDispatcher("WEB-INF/_personal_blog_single_article.jsp").forward(req,resp);
            } else {
                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write("<h3>no more articles</h3>");
            }
            cleanAllParameters(req);
            return;
        }

        /*Load comments tree*/
        if (loadCommentTreeController(req, resp)) return;

        /*Read more content*/
        if (loadArticleContentController(req, resp)) return;

        /*loading article edit area*/
        if (loadEditArticleController(req, resp)) return;

        /*Create new article*/
        if (createArticleController(req, resp)) return;

        /*Delete article*/
        if (deleteArticleController(req, resp)) return;

        /*Delete comment*/
        if (deleteCommentController(req, resp)) return;

        /*create comment*/
        if (createCommentController(req, resp)) return;

        /*edit comment*/
        if (editCommentController(req, resp)) return;

    }

    private boolean loadCommentTreeController(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String articleId = req.getParameter("showCommentsOfArticle");
        if (articleId != null) {
            //Blog blog = DbConnector.getBlogByArticleId(articleId);
            Comments comments = DbConnector.getCommentsByArticleId(articleId);
            Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> commentTree = comments.getCommentTree();
            ajaxCommentsHandler(commentTree, req, resp);
            cleanAllParameters(req);
            return true;
        }
        return false;
    }

    private boolean loadArticleContentController(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String articleId;
        articleId = req.getParameter("loadContentOfArticle");
        if (articleId != null) {
            Blog blog = DbConnector.getBlogByArticleId(articleId);
            ajaxArticleContentHandler(blog, req, resp);
            cleanAllParameters(req);
            return true;
        }
        return false;
    }

    private boolean loadEditArticleController(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String articleToEdit = req.getParameter("editArticle");
        if (articleToEdit != null){
            req.setAttribute("articleId", articleToEdit);
            req.getRequestDispatcher("WEB-INF/_personal_blog_create.jsp").forward(req,resp);
            cleanAllParameters(req);
            return true;
        }
        return false;
    }

    private boolean deleteCommentController(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String commentToDelete = req.getParameter("deleteComment");
        if (commentToDelete != null){
            DbConnector.deleteCommentById(commentToDelete);
            cleanAllParameters(req);

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("deleted");
            return true;
        }
        return false;
    }

    private boolean editCommentController(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String commentToEdit = req.getParameter("editComment");
        if (commentToEdit != null){
            CommentRecord comment = new CommentRecord();

            comment.setId(Integer.parseInt(commentToEdit));
            comment.setContent(req.getParameter("content"));
            comment.setEditTime(new Timestamp(System.currentTimeMillis()));

            DbConnector.updateExistingComment(comment);
            cleanAllParameters(req);

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("updated");
            return true;
        }
        return false;
    }

    private boolean createCommentController(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String parentArticle = req.getParameter("replyArticle");
        if (parentArticle != null){
            CommentRecord comment = new CommentRecord();

            comment.setCommenter(Integer.parseInt(req.getParameter("commenter")));
            comment.setContent(req.getParameter("content"));
            comment.setParentArticle(Integer.parseInt(req.getParameter("replyArticle")));
            comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
            if (req.getParameter("replyComment")!=null){
                comment.setParentComment(Integer.parseInt(req.getParameter("replyComment")));
            }

            DbConnector.insertNewComment(comment);
            cleanAllParameters(req);

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("inserted");
            return true;
        }
        return false;
    }

    private boolean deleteArticleController(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String articleToDelete = req.getParameter("deleteArticle");
        if (articleToDelete != null){
            DbConnector.deleteArticleById(articleToDelete);
            cleanAllParameters(req);

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("deleted");
            return true;
        }
        return false;
    }

    private boolean createArticleController(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String articleStr = req.getParameter("newArticle");
        if (articleStr != null) {
            try {
                JSONObject article = (JSONObject) new JSONParser().parse(articleStr);
                ArticleRecord articleRecord = new ArticleRecord();

                articleRecord.setId(Integer.valueOf((String) article.get("articleId")));

                articleRecord.setTitle((String) article.get("title"));
                articleRecord.setContent((String) article.get("content"));
                articleRecord.setAuthor(((Long)article.get("authorId")).intValue());
                articleRecord.setValidTime(new Timestamp((Long) article.get("validTime")));


                String msg;
                if (articleRecord.getId()>0){
                    //update existing article
                    articleRecord.setEditTime(new Timestamp((Long) article.get("createTime")));
                    msg = DbConnector.updateExistingArticle(articleRecord) ? "updated" : "error";

                } else {
                    //insert new article
                    articleRecord.setCreateTime(new Timestamp((Long) article.get("createTime")));
                    msg = DbConnector.insertNewArticle(articleRecord) ? "inserted" : "error";
                }

                System.out.println(articleRecord);

                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(msg);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            cleanAllParameters(req);
            return true;
        }
        return false;
    }

    /**
     --  ██╗  ██╗███████╗██╗     ██████╗ ███████╗██████╗     ███████╗██╗   ██╗███╗   ██╗ ██████╗
     --  ██║  ██║██╔════╝██║     ██╔══██╗██╔════╝██╔══██╗    ██╔════╝██║   ██║████╗  ██║██╔════╝
     --  ███████║█████╗  ██║     ██████╔╝█████╗  ██████╔╝    █████╗  ██║   ██║██╔██╗ ██║██║
     --  ██╔══██║██╔══╝  ██║     ██╔═══╝ ██╔══╝  ██╔══██╗    ██╔══╝  ██║   ██║██║╚██╗██║██║
     --  ██║  ██║███████╗███████╗██║     ███████╗██║  ██║    ██║     ╚██████╔╝██║ ╚████║╚██████╗
     --  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ╚══════╝╚═╝  ╚═╝    ╚═╝      ╚═════╝ ╚═╝  ╚═══╝ ╚═════╝
     */

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
