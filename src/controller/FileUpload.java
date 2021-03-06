package controller;

import ORM.tables.records.UserRecord;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utililties.FileUtilities;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

public class FileUpload extends HttpServlet {

    private String fullPath = "";
    private UserRecord user = null;


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        doPost(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        //initial the fullPath
        this.fullPath = getServletContext().getRealPath("/UploadedFile/");
        FileUtilities.createDirection(new File(this.fullPath));
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        //deal with those invalid upload situation
        if (!isMultipart) {
            String articleId = request.getParameter("articleId");
            String commentId = request.getParameter("commentId");
            String parameterString = "";
            if (articleId != null && !articleId.equals("")) {
                parameterString = "?articleId=" + articleId;
            }
            if (commentId != null && !commentId.equals("")) {
                parameterString = "?commentId=" + commentId;
            }
            request.setAttribute("uploadedFiles", "<p>You have not uploaded any file.</p>");
            request.setAttribute("parameterString", parameterString);
            request.getRequestDispatcher("/WEB-INF/_upload_file.jsp" + parameterString).forward(request, response);
            return;
        }
        //set the path by userId retrieve from session
        if (request.getParameter("userId") != null) {
            setPathByUserId(request.getParameter("userId"), request, response);
        } else {
            setPathByUserId(request, response);
        }
        //upload all files
        uploadFile(request, response);
    }

    private void uploadFile(HttpServletRequest request, HttpServletResponse response) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        int maxMemSize = 5 * 1024 * 1024;
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        File theDir = new File(this.fullPath + "temp/");
        FileUtilities.createDirection(theDir);
        factory.setRepository(theDir);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        int maxFileSize = 50 * 1024 * 1024;
        upload.setSizeMax(maxFileSize);
        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            StringBuilder fileUploadResultString = new StringBuilder("<p>You have uploaded multimedia as follow:</p><br>" +
                    "<script>setTimeout(function () { history.back()}, 1500);</script>" +
                    "<table><tr><td>title</td><td>thumbnail</td><tr>");
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
                    String fileName = fi.getName();
                    long sizeInBytes = fi.getSize();
                    //filter all other files
                    String[] fileNames = fileName.split("\\.");
                    String fileTypeFlag = "";
                    String theFileName = "";
                    if (fileNames.length == 2) {
                        theFileName = fileNames[0];
                        fileTypeFlag = fileNames[1];
                    } else {
                        theFileName = fileName.substring(0, fileName.lastIndexOf("."));
                        fileTypeFlag = fileName.substring(fileName.lastIndexOf(".") + 1);
                    }
                    if (!(FileUtilities.checkMultimediaType(fileTypeFlag))) {
                        request.setAttribute("uploadedFiles", "<p>Your upload file including invalid type.</p>");
                        request.getRequestDispatcher("/WEB-INF/_upload_file.jsp").forward(request, response);
                        return;
                    }
                    //filter all those images which are large than the maxMemSize
                    if (sizeInBytes > maxMemSize) {
                        request.setAttribute("uploadedFiles", "<p>Your upload file is too large.</p>");
                        request.getRequestDispatcher("/WEB-INF/_upload_file.jsp").forward(request, response);
                        return;
                    }
                    // Write the file
                    String uploadFileName = "";
                    if (fileName.lastIndexOf("\\") >= 0) {
                        uploadFileName = fileName.substring(fileName.lastIndexOf("\\"));
                    } else {
                        uploadFileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                    }
                    File file = new File(this.fullPath + uploadFileName);
                    fi.write(file);
                    //create thumbnail for the file
                    FileUtilities.createThumbnail(this.fullPath, getServletContext().getRealPath("/pictures/"), uploadFileName);
                    String articleId = request.getParameter("articleId");
                    String commentId = request.getParameter("commentId");
                    String userId = request.getParameter("userId");
                    String attachType = "U";
                    Integer ownby = 0;
                    if (this.user != null) {
                        ownby = this.user.getId();
                    }
                    if (articleId != null && !articleId.equals("")) {
                        attachType = "A";
                        ownby = Integer.parseInt(articleId);
                    }
                    if (commentId != null && !commentId.equals("")) {
                        attachType = "C";
                        ownby = Integer.parseInt(commentId);
                    }
                    /* There is no session at that time for us to get user info, because new user not logged in yet*/
                    if (userId != null && !userId.equals("")) {
                        attachType = "U";
                        ownby = Integer.parseInt(userId);
                    }
                    System.out.println("ownby = [" + ownby + "]");

                    // save the information into database
                    int userPathId = this.user != null ? this.user.getId() : Integer.parseInt(userId != null ? userId : "0");

                    FileUtilities.saveInformationToDB(theFileName, "UploadedFile/multimedia/" + userPathId + "/", fileTypeFlag, attachType, ownby);
                    //store the result information
                    fileUploadResultString.append("<tr><td>").append(theFileName).append("</td><td><img src='UploadedFile/multimedia/").append(userPathId).append("/").append(theFileName).append("_thumbnail.jpg' alt='").append(theFileName).append("'></td></tr>");
                }
            }
            fileUploadResultString.append("</table><input type='button' value='Go back' onclick='goBack()'>");
            response.getWriter().write(FileUtilities.getShowingString(fileUploadResultString.toString()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setPathByUserId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        //create direction for multimedia
        this.fullPath += "/multimedia/";
        File theDir = new File(this.fullPath);
        FileUtilities.createDirection(theDir);
        //get the user from session
        this.user = getLoggedUserFromSession(request);
        if (this.user == null) {
            request.setAttribute("uploadedFiles", "<p>You should login first.</p>");
            request.getRequestDispatcher("/WEB-INF/_upload_file.jsp").forward(request, response);
            return;
        }
        this.fullPath += this.user.getId() + "/";
        theDir = new File(this.fullPath);
        FileUtilities.createDirection(theDir);
    }

    /**
     * For new user uploading avatar
     */
    private void setPathByUserId(String userId, HttpServletRequest request, HttpServletResponse response) {
        //create direction for multimedia
        this.fullPath += "/multimedia/";
        File theDir = new File(this.fullPath);
        FileUtilities.createDirection(theDir);

        this.fullPath += userId + "/";
        theDir = new File(this.fullPath);
        FileUtilities.createDirection(theDir);
    }

    /**
     * @return Logged User, or null if not logged in
     */
    private UserRecord getLoggedUserFromSession(HttpServletRequest req) {
        // If current session does not exist, then it will NOT create a new session.
        HttpSession session = req.getSession(false);

        UserRecord user;
        if (session == null || session.getAttribute("loggedInUser") == null) {
            user = null;
        } else {
            user = (UserRecord) session.getAttribute("loggedInUser");
        }
        return user;
    }

}