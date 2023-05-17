package Servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.Map;

@WebServlet(name = "Registration", value = "/Registration")
public class Registration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Registration</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <form method=\"POST\" action=\"http://localhost:8080/water/Registration\">\n" +
                "        <label for=\"username\">Login:</label>\n" +
                "        <input type=\"text\" name=\"username\" id=\"username\">\n" +
                "        <br>\n" +
                "        <label for=\"password\">Password:</label>\n" +
                "        <input type=\"password\" name=\"password\" id=\"password\">\n" +
                "        <br>\n" +
                "        <button type=\"submit\">Save</button>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryString = request.getQueryString();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String username = parameterMap.get("username")[0];
        String password = parameterMap.get("password")[0];
        response.setContentType("text/plain");
        response.getWriter().write("Username: " + username + ", Password: " + password);
    }
}
