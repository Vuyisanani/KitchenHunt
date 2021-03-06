/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import HibFiles.PoolManager;
import HibFiles.SystemStatus;
import HibFiles.User;
import HibFiles.UserLogin;
import HibFiles.UserType;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author User
 */
public class SignUp extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        System.out.println("inside servlet");

        try {

            //Get attributes from http request
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String mobile = request.getParameter("mobile");
            String upassword = request.getParameter("password");
            String confirmpassword = request.getParameter("confirmpassword");

            System.out.println(name + email + mobile + upassword);

            String msg = "";

            if (upassword.equals(confirmpassword)) {

                //Create hibernate session
                Session s = PoolManager.getSessionFactory().openSession();

                //Initiate transaction
                Transaction t = s.beginTransaction();

                //Get system status
                Criteria c = s.createCriteria(SystemStatus.class);
                c.add(Restrictions.eq("statusName", "Active"));
                SystemStatus ss = (SystemStatus) c.uniqueResult();

                //Get user type
                Criteria c1 = s.createCriteria(UserType.class);
                c1.add(Restrictions.eq("typeName", "User"));
                UserType ut = (UserType) c1.uniqueResult();

                //Get user login
                Criteria c2 = s.createCriteria(UserLogin.class);
                c2.add(Restrictions.eq("email", email));
                UserLogin ul = (UserLogin) c2.uniqueResult();

                if (ul == null) {

                    //If user does not exist, create new user and save
                    User u = new User();
                    u.setFname(name);
                    u.setMobile(mobile);
                    u.setRegisterDate(new Date());
                    u.setUserType(ut);

                    s.save(u);

                    //Create new user login and save
                    UserLogin ul1 = new UserLogin();
                    ul1.setEmail(email);
                    ul1.setPassword(Security.encrypt(upassword));
                    ul1.setUser(u);
                    ul1.setSystemStatus(ss);

                    s.save(ul1);

                    //Commit changes
                    t.commit();

                    //Send Verification Email
                    text = "Hi " + name + ",<br><br>Thanks for creating your new Kitchen Hunt account.<br><br>Now you can enjoy the features of your My Kitchen account. <br><br>If you did not create a Kitchen Hunt account, just ignore this message.";
                    System.out.println(text);
                    System.out.println("Sending verification email");
                    to = email;
                    sendMail();
                    System.out.println("Verification email sent");

                    msg = "success";

                } else {
                    msg = "Error1";
                }

                System.out.println(name + " " + email + " " + mobile + " " + upassword + " " + confirmpassword);

            } else {
                msg = "Error2";
            }

            out.write("success");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private static class MailAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(sender_email, password);
        }
    }
    static String sender_email = "kitchenhunt.com@gmail.com";
    static String password = "kitchenhunt@123";
    static String host = "smtp.gmail.com";
    static String port = "587";
    static String to = "";
    static String subject = "Welcome to Kitchen Hunt";
    static String text = "";

    public static void sendMail() {
        Properties props = new Properties();
        props.put("mail.smtp.user", sender_email);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        try {
            Authenticator auth = new MailAuthenticator();
            // Get the default Session object.
            javax.mail.Session session = javax.mail.Session.getInstance(props, auth);

            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(sender_email));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject(subject);
            // Send the actual HTML message
            message.setContent(text, "text/html");

            // Send message
            Transport.send(message);

        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
