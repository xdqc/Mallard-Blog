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

        List<Tuple<?,?>> allRecords = DbConnector.getAllRecords();

        List<UserRecord> userResults = allRecords.stream()
                .filter(r -> r.Val1 instanceof UserRecord)
                .map(r -> (UserRecord)r.Val1)
                .collect(Collectors.toList());

        List<Tuple<ArticleRecord, UserRecord>> articleResults = allRecords.stream()
                .filter(r -> r.Val1 instanceof ArticleRecord)
                .map(r -> (Tuple<ArticleRecord, UserRecord>)r)
                .collect(Collectors.toList());

        List<Tuple<CommentRecord, UserRecord>> commentResults = allRecords.stream()
                .filter(r -> r.Val1 instanceof CommentRecord)
                .map(r -> (Tuple<CommentRecord, UserRecord>)r)
                .collect(Collectors.toList());

        req.setAttribute("userResults", userResults);
        req.setAttribute("articleResults", articleResults);
        req.setAttribute("commentResults", commentResults);
        req.getRequestDispatcher("WEB-INF/admin.jsp").forward(req, resp);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
