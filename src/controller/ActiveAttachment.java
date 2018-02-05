package controller;

import ORM.tables.records.AttachmentRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ActiveAttachment extends Controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String attachmentId = req.getParameter("entityId");
        String parameterName = req.getParameter("parameterName");
        String attachType = "";
        if(parameterName.equals("article")){
            attachType = "A";
        }
        if(parameterName.equals("comment")){
            attachType = "C";
        }
        if(parameterName.equals("user")){
            attachType = "U";
        }
        //get all articles sort by like number
        AttachmentRecord attachment = DbConnector.getActiavedAttachment(attachmentId,attachType);
        String result = "";
        if(attachment != null) {
            result += "<img src=\"" + attachment.getPath() + attachment.getFilename() + "." + attachment.getMime() + "\" class=\"panel-img-top img-responsive\" alt=\"" + attachment.getFilename() + "\" />";
        }
        resp.setContentType("text/html");
        if(result.equals("")) {
            resp.getWriter().write("<p>There has not any multimedia.Please upload some what you like.<p>");
        }else{
            resp.getWriter().write(result);
        }
        return;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
