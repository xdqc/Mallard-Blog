package controller;


import ORM.tables.records.AttachmentRecord;
import db_connector.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AttachmentManage extends Controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //dispatch the different operation
        switch(req.getParameter("operateName").toLowerCase()){
            case "activatelist" :
                generateActiveList(req,resp,null);
                break;
            case "activate" :
                activateFile(req,resp);
                break;
        }
        //DbConnector.setActivePicture(attachmentId);
    }

    //deal with set active file
    private void activateFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String attachmentId = req.getParameter("attachmentId");
        System.out.println("attachmentId = [" + attachmentId + "]");
        //translate the attachment id to ownby id
        String ownby = DbConnector.getOwnbyByAttachmentId(attachmentId);
        DbConnector.setActivePicture(attachmentId);
        //DbConnector.deleteAttachmentById(attachmentId);
        generateActiveList(req,resp,ownby);
    }

    //generate active list
    private void generateActiveList(HttpServletRequest req, HttpServletResponse resp,String ownby) throws IOException{
        String attachmentId = req.getParameter("entityId");
        String parameterName = req.getParameter("parameterName");
        if(ownby != null){
            attachmentId = ownby;
        }
        System.out.println("parameterName = [" + parameterName + "], attachmentId = [" + attachmentId + "]");
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
        List<AttachmentRecord> attachments ;
        attachments = DbConnector.getEntityAttachments(attachmentId,attachType);
        attachments.forEach( a -> System.out.println("a.getId() = [" + a.getId() + "]" + "a.getFilename() = [" + a.getFilename() + "]"));
        req.setAttribute("attachments", attachments);

        StringBuilder result = new StringBuilder();
        for (AttachmentRecord attachment : attachments) {
            result.append("<tr>");
            result.append("<td>").append(attachment.getFilename()).append("</td><td><a href=\"").append(attachment.getPath()).append(attachment.getFilename()).append(".").append(attachment.getMime()).append("\"><img src=\"").append(attachment.getPath()).append(attachment.getFilename()).append("_thumbnail.jpg\" alt=\"").append(attachment.getFilename()).append("\"></a></td>");
            //operate setting
            if(attachment.getIsactivate().toString().equals("0")) {
                result.append("<td>" + "<span id=\"showMultimedia-Activate-").append(parameterName.toLowerCase()).append("-").append(attachment.getId()).append("\" class=\"activate-item btn btn-success\" ><span class=\"fa fa-pencil\"></span>Activate</span>").append("</td>");
            }else{
                result.append("<td>" + "<span>Activated</span>" + "</td>");

            }

            result.append("</tr>");
        }
        resp.setContentType("text/html");
        if(result.toString().equals("")) {
            resp.getWriter().write("<p>There has not any multimedia.Please upload some what you like.<p>");
        }else{
            resp.getWriter().write(getShowString(result.toString(),attachmentId));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    private String getShowString(String insertContent,String articleId){
        return "<table><tr><td>File name</td><td>Thumbnail</td><td>Operate</td></tr>"
                + insertContent +
                "</table>"
                + "<script>\n" +
                "$(document).ready(function () {\n" +
                "    $('.activate-item').click(function() {\n" +
                "        const attachmentId = getEntityId($(this));\n" +
                "        const parameterName = getEntityParameterName($(this));\n" +
                "        const userCheck = isUserCheck($(this));\n" +
                "        if(userCheck.toLowerCase() == \"activate\" ){\n" +
                "            $.ajax({\n" +
                "                url : 'Attachment-Manage',\n" +
                "                data : {\n" +
                "                    attachmentId : attachmentId,\n" +
                "                    parameterName : parameterName,\n" +
                "                    operateName : userCheck\n" +
                "                },\n" +
                "                beforeSend: () => {\n" +
                "                    $(\"#load-article-img\").css(\"display\", \"block\");\n" +
                "                }," +
                "                success : function(responseText) {\n" +
                "                    $('#multimediaShowArea-' + parameterName + '-'+" + articleId + ").html(responseText);\n" +
                "                    $('#showActivatedMultimedia-' + parameterName + '-'+" + articleId + ").click();\n" +
                "                },\n" +
                "                complete: () => {\n" +
                "                    $(\"#load-article-img\").css(\"display\", \"none\");\n" +
                "                }" +
                "            });\n" +
                "        }\n" +
                "    });\n" +
                "})\n" +
                "</script>\n";
    }
}
