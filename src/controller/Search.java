package controller;

import ORM.tables.User;
import ORM.tables.records.ArticleRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import org.jooq.Record;
import utililties.Tuple;

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
            searchStr = searchStr.trim();
            if (searchStr.isEmpty()){
                req.getRequestDispatcher("error?&errorMsg=" + "Please enter something to search.").forward(req, resp);
            }



            String[] searchItems = searchStr.split("\\s+");
            for (int i = 0; i < searchItems.length; i++) {
                searchItems[i] = searchItems[i].trim();
            }

            List<Tuple<?,?>> searchResults = DbConnector.findSearchItems(searchItems);

            List<UserRecord> userResults = searchResults.stream()
                    .filter(r -> r.Val1 instanceof UserRecord)
                    .map(r -> (UserRecord)r.Val1)
                    .collect(Collectors.toList());

            List<Tuple<ArticleRecord, UserRecord>> articleResults = searchResults.stream()
                    .filter(r -> r.Val1 instanceof ArticleRecord)
                    .map(r -> (Tuple<ArticleRecord, UserRecord>)r)
                    .collect(Collectors.toList());

//            articleResults.get(0).Val1.getValidTime().getTime();

            req.setAttribute("searchStr", searchStr);
            req.setAttribute("userResults", userResults);
            req.setAttribute("articleResults", articleResults);
            req.getRequestDispatcher("search_result.jsp").forward(req, resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
