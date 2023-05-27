package controller;


import action.*;
import entity.Role;
import entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class SecurityFilter implements Filter {
    private static Logger logger = Logger.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            Action action = (Action)httpRequest.getAttribute("action");
            Set<Role> allowRoles = action.getAllowRoles();
            String userName = "unauthorized user";
            HttpSession session = httpRequest.getSession(false);
            User user = null;
            if(session != null) {
                user = (User)session.getAttribute("authorizedUser");
                action.setAuthorizedUser(user);
                String errorMessage = (String)session.getAttribute("SecurityFilterMessage");
                if(errorMessage != null) {
                    httpRequest.setAttribute("message", errorMessage);
                    session.removeAttribute("SecurityFilterMessage");
                }
            }
            boolean canExecute = allowRoles == null;
            if(user != null) {
                userName = "\"" + user.getUsername() + "\" user";
                canExecute = canExecute || allowRoles.contains(user.getRole());
            }
            System.out.println("CanExecute: " + canExecute);
            if(canExecute) {
                chain.doFilter(request, response);
            } else {
                logger.info(String.format("Trying of %s access to forbidden resource \"%s\"", userName, action.getName()));
                if(session != null && action.getClass() != MainAction.class) {
                    session.setAttribute("SecurityFilterMessage", "Доступ запрещён");
                }
                System.out.println(httpRequest.getContextPath()+"/login.html");
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {}
}
