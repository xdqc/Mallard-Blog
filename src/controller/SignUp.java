package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUp extends Controller {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("error?errorMsg=You think you are smart again?").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        if (loggedUserRedirectTo("edit_profile.jsp", req, resp))
            return;


        if(redirectTo("signUp=0", "WEB-INF/sign_up.jsp", req, resp))
            return;



        boolean isSignedIn = req.getParameter("signUp") != null && req.getParameter("signUp").equals("1");
        if (isSignedIn){
            //TODO insert info to db


            req.getRequestDispatcher("upload_profile.jsp").forward(req, resp);
            return;
        }


    }


}
