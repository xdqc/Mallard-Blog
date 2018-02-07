package controller;

import ORM.tables.records.AttachmentRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            if(attachment.getMime().equals("mp4")) {
                result += "<video class=\"panel-img-top img-responsive\" controls>\n" +
                        "  <source src=\"" + attachment.getPath() + attachment.getFilename() + ".mp4\" type=\"video/mp4\">\n" +
                        "  Your browser does not support the video tag.\n" +
                        "</video>";
            }else{
                result += "<img src=\"" + attachment.getPath() + attachment.getFilename() + "." + attachment.getMime() + "\" class=\"panel-img-top img-responsive\" alt=\"" + attachment.getFilename() + "\" />";
            }
        }
        resp.setContentType("text/html");
        if(result.equals("")) {
            resp.getWriter().write("<p>There is no multimedia. Please upload some what you like.<p>");
        }else{
            resp.getWriter().write(result);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
