package controller;

import ORM.tables.Attachment;
import ORM.tables.records.AttachmentRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MultimediaGallery  extends Controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRecord user = getLoggedUserFromSession(req);

        String articleId = req.getParameter("articleId");

        //get all articles sort by like number
        List<AttachmentRecord> attachments = DbConnector.getAttachmentByArticleId(articleId);
        req.setAttribute("attachments", attachments);

        req.getRequestDispatcher("/multimedia_gallery.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }

}
