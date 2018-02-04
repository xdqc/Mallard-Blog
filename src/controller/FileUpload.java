package controller;

import ORM.tables.records.AttachmentRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utililties.Blog;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

public class FileUpload extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024* 1024;
    private int maxMemSize = 5 * 1024* 1024;
    private File file ;
    private String fullPath = "";
    private UserRecord user = null;
    private String respondPath = "";
    private Map parameter = new HashMap();



    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        doPost(request,response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        //initial the fullPath
        this.fullPath = getServletContext().getRealPath("/UploadedFile/");
        createDirection(new File(this.fullPath));
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        //deal with those invalid upload situation
        if( !isMultipart ) {
            String articleId = request.getParameter("articleId");
            String commentId = request.getParameter("commentId");
            String parameterString = "";
            if(articleId != null && !articleId.equals("")){
                parameterString = "?articleId=" + articleId;
            }
            if(commentId != null && !commentId.equals("")){
                parameterString = "?commentId=" + commentId;
            }
            request.setAttribute("uploadedFiles","<p>You have not uploaded any file.</p>");
            request.setAttribute("parameterString",parameterString);
            request.getRequestDispatcher("/WEB-INF/_upload_file.jsp" + parameterString).forward(request, response);
            return;
        }
        //set the path by userId retrieve from session
        setPathByUserId(request, response);

        //upload all files
        uploadFile(request, response);
    }

    private void uploadFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException{
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        File theDir = new File(this.fullPath + "temp/");
        createDirection(theDir);
        factory.setRepository(theDir);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );
        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            String fileUploadResultString =  "<p>You have uploaded multimedia as follow:</p><br>" +
                    "<script>setTimeout(function () { history.back()}, 1500);</script>" +
                                             "<table><tr><td>title</td><td>thumbnail</td><tr>";
            while ( i.hasNext () ) {
                FileItem fi = (FileItem)i.next();
                if ( !fi.isFormField () ) {
                    // Get the uploaded file parameters
                    String fileName = fi.getName();
                    long sizeInBytes = fi.getSize();
                    //filter all other files
                    String[] fileNames = fileName.split("\\.");
                    String fileTypeFlag = "";
                    String theFileName = "";
                    if(fileNames.length == 2) {
                        theFileName = fileNames[0];
                        fileTypeFlag = fileNames[1];
                    }else{
                        theFileName = fileName.substring(0,fileName.lastIndexOf("."));
                        fileTypeFlag = fileName.substring(fileName.lastIndexOf(".") + 1);
                    }
                    if(!(checkMultimediaType(fileTypeFlag))){
                        request.setAttribute("uploadedFiles","<p>Your upload file including invalid type.</p>");
                        request.getRequestDispatcher("/WEB-INF/_upload_file.jsp").forward(request, response);
                        return;
                    }
                    //filter all those images which are large than the maxMemSize
                    if(sizeInBytes > maxMemSize){
                        request.setAttribute("uploadedFiles","<p>Your upload file is too large.</p>");
                        request.getRequestDispatcher("/WEB-INF/_upload_file.jsp").forward(request, response);
                        return;
                    }
                    // Write the file
                    String uploadFileName = "";
                    if( fileName.lastIndexOf("\\") >= 0 ) {
                        uploadFileName = fileName.substring( fileName.lastIndexOf("\\"));
                    } else {
                        uploadFileName = fileName.substring( fileName.lastIndexOf("\\")+1);
                    }
                    this.file = new File( this.fullPath + uploadFileName);
                    fi.write(this.file) ;
                    //create thumbnail for the file
                    createThumbnail(uploadFileName);

                    String articleId = request.getParameter("articleId");
                    System.out.println("articleId = [" + articleId + "]");
                    String commentId = request.getParameter("commentId");
                    /* To Tonny: This is for newly created user to choose avatar, need to accept "userId" as parameter - samuel*/
                    /* There is no session at that time, because new user not logged in yet*/
                    String userId = request.getParameter("userID");
                    String attachType = "U";
                    Integer ownby = this.user.getId();
                    if(articleId != null && !articleId.equals("")){
                        attachType = "A";
                        ownby = Integer.parseInt(articleId);
                    }
                    if(commentId != null && !commentId.equals("")){
                        attachType = "C";
                        ownby = Integer.parseInt(commentId);
                    }
                    /* There is no session at that time for us to get user info, because new user not logged in yet*/
                    if (userId != null && !userId.equals("")){
                        attachType = "U";
                        ownby = Integer.parseInt(userId);
                    }
                    System.out.println("ownby = [" + ownby + "]");
                    // save the information into database
                    saveInformationToDB(theFileName,fileTypeFlag,attachType, ownby);
                    //store the result information
                    fileUploadResultString += "<tr><td>" + theFileName + "</td><td><img src='/UploadedFile/multimedia/"
                                            + this.user.getId() + "/" + theFileName + "_thumbnail.png' alt='" + theFileName + "'></td></tr>";
                }else{
                    if(fi.getFieldName().equals("respondPath")) {
                        this.respondPath = fi.getString();
                    }else{
                        this.parameter.put(fi.getFieldName(),fi.getString());
                        System.out.println("fi.getFieldName() = [" + fi.getFieldName() + "], fi.getFieldName() = [" + fi.getFieldName() + "]");
                    }
                }
            }
            fileUploadResultString += "</table><input type='button' value='Go back' onclick='goBack()'>";
            //request.setAttribute("uploadedFiles",fileUploadResultString);

            response.getWriter().write(getShowingString(fileUploadResultString));
            //System.out.println("getShowingString(fileUploadResultString) = [" + getShowingString(fileUploadResultString) + "]");
            return;
            /*

            Set s = this.parameter.keySet();
            for (Object o : s) {
                System.out.println("o = [" + o.toString() + "], parameter = [" + this.parameter.get((String) o ) + "]");
                request.setAttribute(o.toString(),this.parameter.get((String) o ));
            }
            List<Blog> blogs = DbConnector.getBlogsByUserId(this.user.getId().toString());
            request.setAttribute("blogs", blogs);
            request.getRequestDispatcher(this.respondPath).forward(request, response);
            return;
             */
        }catch(FileUploadException ex) {
            System.out.println(ex);
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }
    //save the file information into database
    private boolean saveInformationToDB(String filename, String mime, String attachType, Integer ownby){
        return DbConnector.saveAttachmentRecord(new AttachmentRecord(null,filename, "/UploadedFile/multimedia/" + user.getId() + "/", mime, attachType, ownby, new Byte("1")));
    }
    // create the thumbnail for the multimedia
    private void createThumbnail(String uploadFileName)throws java.io.IOException{
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);

        String[] fileNames = uploadFileName.split("\\.");
        String fileTypeFlag = "";
        String theFileName = "";
        if(fileNames.length == 2) {
            theFileName = fileNames[0];
            fileTypeFlag = fileNames[1];
        }else{
            theFileName = uploadFileName.substring(0,uploadFileName.lastIndexOf("."));
            fileTypeFlag = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        }
        if(fileTypeFlag.equals("mp4")||fileTypeFlag.equals("mpg")||fileTypeFlag.equals("avi")||fileTypeFlag.equals("mpeg")) {
            System.out.println("getServletContext = [" + getServletContext().getRealPath("") + "]");
            img.createGraphics().drawImage(ImageIO.read(new File(getServletContext().getRealPath("/pictures/") + "mp4.png")).getScaledInstance(50, 50, Image.SCALE_SMOOTH), 0, 0, null);
        }else {
            img.createGraphics().drawImage(ImageIO.read(new File(this.fullPath + uploadFileName)).getScaledInstance(50, 50, Image.SCALE_SMOOTH), 0, 0, null);
        }
        System.out.println(this.fullPath + theFileName + "_thumbnail.png");

        ImageIO.write(img, "png", new File(this.fullPath + theFileName + "_thumbnail.png" ));
    }
    //check the media type is valid
    private boolean checkMultimediaType(String extend){
        if(extend.equals("jpg") || extend.equals("png") || extend.equals("mp4")
                || extend.equals("mpg")|| extend.equals("mpeg")|| extend.equals("avi")
                /*
                || extend.equals("wmv")|| extend.equals("mov")|| extend.equals("rm")
                || extend.equals("ram")|| extend.equals("swf")|| extend.equals("flv")
                || extend.equals("ogg")|| extend.equals("webm")|| extend.equals("mp3")
                || extend.equals("wav")|| extend.equals("wma")|| extend.equals("wav")
                */
                ){
            return true;
        }
        return false;
    }
    private void setPathByUserId (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        //create direction for multimedia
        this.fullPath += "/multimedia/" ;
        File theDir = new File(this.fullPath);
        createDirection(theDir);
        //get the user from session
        this.user = getLoggedUserFromSession(request);
        if(this.user == null){
            request.setAttribute("uploadedFiles","<p>You should login first.</p>");
            request.getRequestDispatcher("/WEB-INF/_upload_file.jsp").forward(request, response);
            return;
        }
        this.fullPath += this.user.getId() + "/";
        theDir = new File(this.fullPath);
        createDirection(theDir);
    }

    private void createDirection(File theDir){
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            try{
                theDir.mkdir();
            }
            catch(SecurityException se){
                //handle it
                se.printStackTrace();
            }
        }
    }

    /**
     * @return Logged User, or null if not logged in
     */
    protected UserRecord getLoggedUserFromSession(HttpServletRequest req) {
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

    //return the show content
    private String getShowingString(String insertString){
        String result = "<form id=\"resultForm\">\n" +
                "    <fieldset>\n" +
                "        <legend>Your uploaded file</legend>\n" +
                insertString +
                "    </fieldset>\n" +
                "</form>";
        return result;
    }
}