package controller;
import ORM.tables.records.UserRecord;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONObject;

public class reCaptchaValidation extends Controller{

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(isCaptchaValid("6LejNkIUAAAAAFWy-qW_g41LE6qruFIpWWHOdE31", req.getParameter("g-recaptcha-response"))){
            //valid
            //UserRecord user = getLoggedUserFromSession(req);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    public static boolean isCaptchaValid(String secretKey, String response) {
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify?"
                    + "secret=" + secretKey
                    + "&response=" + response;
            InputStream res = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            res.close();

            JSONObject json = new JSONObject(jsonText);
            return json.getBoolean("success");
        } catch (Exception e) {
            return false;
        }
    }



    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
