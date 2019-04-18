package servlets;

import database.Queries;
import java.io.IOException;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(Integer.MAX_VALUE);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + " " + password);
        System.out.println(Queries.userLogin(username, password));
        RequestDispatcher rs;
        if (Queries.userLogin(username, password)) {
            session.setAttribute("username", username);
            request.removeAttribute("loginError");
            rs = request.getRequestDispatcher("/map.jsp");
            rs.forward(request, response);
        } else {
            request.setAttribute("loginError", "Invalid username or password.");
            rs = request.getRequestDispatcher("/login.jsp");
            rs.forward(request, response);
        }
    }

}



