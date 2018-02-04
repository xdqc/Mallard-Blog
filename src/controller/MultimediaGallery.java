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

        String attachmentId = req.getParameter("attachmentId");
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
        if(userID == null || user.equals("")){
            attachments = DbConnector.getAllAttachments(attachmentId,attachType);
        }else{
            attachments = DbConnector.getAttachmentsByUserId(userID,attachmentId);
        }
        attachments.forEach( a -> {
            System.out.println("a.getId() = [" + a.getId() + "]" + "a.getFilename() = [" + a.getFilename() + "]");
        });
        req.setAttribute("attachments", attachments);

        String result = "";
        for (AttachmentRecord attachment : attachments) {
            result += "<a href=\"" + attachment.getPath() + attachment.getFilename() + "." + attachment.getMime() + "\"><img src=\"" + attachment.getPath() + attachment.getFilename() + "_thumbnail.png\" alt=\"" + attachment.getFilename() + "\"></a>";
        }
        resp.setContentType("text/html");
        if(result.equals("")) {
            resp.getWriter().write("<p>There has not any multimedia.Please upload some what you like.<p>");
        }else{
            resp.getWriter().write(getShowString(result));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    private String getShowString(String insertContent){
        String result =
                "    <script type=\"text/javascript\" src=\"../javascript/jquery.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"../javascript/html5gallery.js\"></script>\n" +
                "<div id=\"imagesShowing\" style=\"display:none;\" class=\"html5gallery\" data-skin=\"horizontal\" data-width=\"800\" data-height=\"600\">\n" + insertContent + "</div>\n" ;
        return result;
    }
}
