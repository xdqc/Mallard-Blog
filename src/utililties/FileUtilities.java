package utililties;

import ORM.tables.records.AttachmentRecord;
import db_connector.DbConnector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FileUtilities {

    // create the thumbnail for the multimedia
    public static void createThumbnail( String fullPath,String realPath, String uploadFileName)throws java.io.IOException{
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
            img.createGraphics().drawImage(ImageIO.read(new File( realPath + "mp4.jpg")).getScaledInstance(50, 50, Image.SCALE_SMOOTH), 0, 0, null);
        }else {
            img.createGraphics().drawImage(ImageIO.read(new File(fullPath + uploadFileName)).getScaledInstance(50, 50, Image.SCALE_SMOOTH), 0, 0, null);
        }
        ImageIO.write(img, "jpg", new File(fullPath + theFileName + "_thumbnail.jpg" ));
    }

    //save the file information into database
    public static void saveInformationToDB(String filename, String path, String mime, String attachType, Integer ownby){
        DbConnector.saveAttachmentRecord(new AttachmentRecord(null, filename, path, mime, attachType, ownby, new Byte("0")));
    }

    //check the media type is valid
    public static boolean checkMultimediaType(String extend){
        return extend.equals("jpg") || extend.equals("png") || extend.equals("mp4")
                || extend.equals("mpg") || extend.equals("mpeg") || extend.equals("avi");
    }

    //combine the inserting string and return the show content
    public static String getShowingString(String insertString){
        return "<form id=\"resultForm\">\n" +
                "    <fieldset>\n" +
                "        <legend>Your uploaded file</legend>\n" +
                insertString +
                "    </fieldset>\n" +
                "</form>";
    }

    //deal with direction
    public static void createDirection(File theDir){
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
}
