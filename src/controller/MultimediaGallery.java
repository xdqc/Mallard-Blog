package controller;

import ORM.tables.records.AttachmentRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MultimediaGallery extends Controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRecord user = getLoggedUserFromSession(req);

        String attachmentId = req.getParameter("entityId");
        String parameterName = req.getParameter("parameterName");
        String userID = req.getParameter("userID");
        String attachType = "";
        if(parameterName.equals("article")){
            attachType = "A";
        }
        if(parameterName.equals("comment")){
            attachType = "C";
        }
        //get all articles sort by like number
        List<AttachmentRecord> attachments ;
        if(userID == null || userID.equals("")){
            attachments = DbConnector.getAllAttachments(attachmentId,attachType);
        }else{
            attachments = DbConnector.getAttachmentsByUserId(userID,attachmentId);
        }
        req.setAttribute("attachments", attachments);

        StringBuilder result = new StringBuilder();
        for (AttachmentRecord attachment : attachments) {
            result.append("<a href=\"").append(attachment.getPath()).append(attachment.getFilename()).append(".").append(attachment.getMime()).append("\"><img src=\"").append(attachment.getPath()).append(attachment.getFilename()).append("_thumbnail.jpg\" alt=\"").append(attachment.getFilename()).append("\"></a>");
        }
        resp.setContentType("text/html");
        if(result.toString().equals("")) {
            resp.getWriter().write("<p>There has not any multimedia.Please upload some what you like.<p>");
        }else{
            resp.getWriter().write(getShowString(result.toString()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    private String getShowString(String insertContent){
        //        data-width="800" data-height="600"
        return "    <script type=\"text/javascript\" src=\"../javascript/html5gallery.js\"></script>\n" +
        "<div id=\"imagesShowing\" style=\"display:none;\" class=\"html5gallery\" data-skin=\"horizontal\" data-responsive=\"true\" >\n" + insertContent + "</div>\n";
    }
}
