/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author spitlord
 */
public class LoadMapServlet extends HttpServlet {
    public static final String path = "/Users/spitlord/NetBeansProjects/election/saving/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String filename = (String) req.getAttribute("filename");
        String username = (String) req.getSession().getAttribute("username");
        String string = new String(Files.readAllBytes(Paths.get(
                path + username + "/" + filename
        )));
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(string);
    }

}
