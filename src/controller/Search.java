package controller;

import ORM.tables.records.ArticleRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import org.jooq.Record;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Search extends Controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String searchStr = req.getParameter("search");
        if (searchStr != null){
            String[] searchItems = searchStr.split(" ");
            List<Record> searchResults = DbConnector.findSearchItems(searchItems);

            List<UserRecord> userResults = searchResults.stream()
                    .filter(r -> r instanceof UserRecord)
                    .map(r -> (UserRecord)r)
                    .collect(Collectors.toList());

            List<ArticleRecord> articleResults = searchResults.stream()
                    .filter(r -> r instanceof ArticleRecord)
                    .map(r -> (ArticleRecord)r)
                    .collect(Collectors.toList());

            req.setAttribute("userResults", userResults);
            req.setAttribute("articleResults", articleResults);

            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
