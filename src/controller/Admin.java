package controller;

import ORM.tables.records.ArticleRecord;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import utililties.Tuple;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Admin extends Controller {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * Only show admin page to administrator
         */
        UserRecord adminUser = getLoggedUserFromSession(req);
        if (adminUser!=null && adminUser.getSystemRole()==0){
            List<Tuple<?, ?>> allRecords = DbConnector.getAllRecords();

            List<UserRecord> userResults = allRecords.stream()
                    .filter(r -> r.Val1 instanceof UserRecord)
                    .map(r -> (UserRecord) r.Val1)
                    .collect(Collectors.toList());

            List<Tuple<ArticleRecord, UserRecord>> articleResults = allRecords.stream()
                    .filter(r -> r.Val1 instanceof ArticleRecord)
                    .map(r -> (Tuple<ArticleRecord, UserRecord>) r)
                    .collect(Collectors.toList());

            List<Tuple<CommentRecord, UserRecord>> commentResults = allRecords.stream()
                    .filter(r -> r.Val1 instanceof CommentRecord)
                    .map(r -> (Tuple<CommentRecord, UserRecord>) r)
                    .collect(Collectors.toList());

            req.setAttribute("userResults", userResults);
            req.setAttribute("articleResults", articleResults);
            req.setAttribute("commentResults", commentResults);
            req.getRequestDispatcher("WEB-INF/admin.jsp").forward(req, resp);

        } else {

            String errorMsg = "Keep away, intruders!";
            req.getRequestDispatcher("error?errorMsg=" + errorMsg).forward(req, resp);
        }


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * Delete user's own account done by himself
         */
        if (req.getParameter("deleteOwnAccount")!=null){
            String userId = req.getParameter("deleteOwnAccount");

            DbConnector.deleteUserById(userId);

            req.getSession(false).invalidate();
            cleanAllParameters(req);

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("userId= "+userId+" is deleted.");
            return;
        }

        /**
         * Delete user action
         */
        if (req.getParameter("deleteUser")!=null){
            String userId = req.getParameter("deleteUser");

            DbConnector.deleteUserById(userId);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("userId= "+userId+" is deleted.");
            return;
        }

        /**
         * Recover user action
         */
        if (req.getParameter("recoverUser")!=null){
            String userId = req.getParameter("recoverUser");

            DbConnector.recoverUserById(userId);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("userId= "+userId+" is recovered.");
            return;
        }


        /**
         * Show article action
         */
        if (req.getParameter("showArticle")!=null){
            String articleID = req.getParameter("showArticle");

            DbConnector.showArticleById(articleID);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("articleId= "+articleID+" is shown.");
            return;
        }

        /**
         * Hide article action
         */
        if (req.getParameter("hideArticle")!=null){
            String articleId = req.getParameter("hideArticle");

            DbConnector.hideArticleById(articleId);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("articleId= "+articleId+" is hidden.");
            return;
        }


        /**
         * Show comment action
         */
        if (req.getParameter("showComment")!=null){
            String commentId = req.getParameter("showComment");

            DbConnector.showCommentById(commentId);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("commentId= "+commentId+" is shown.");
            return;
        }

        /**
         * Hide comment action
         */
        if (req.getParameter("hideComment")!=null){
            String commentId = req.getParameter("hideComment");

            DbConnector.hideCommentById(commentId);

            cleanAllParameters(req);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("commentId= "+commentId+" is hidden.");
        }

    }


}
