package controller;


import action.*;
import action.authentication.LoginAction;
import action.authentication.LogoutAction;
import action.authentication.RegistrationAction;
import action.chat.ChatAction;
import action.chats.ChatsAction;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionFromUriFilter implements Filter {
    private static Logger logger = Logger.getLogger(ActionFromUriFilter.class);

    private static Map<String, Class<? extends Action>> actions = new ConcurrentHashMap<>();
    private static Map<String, String> actionName = new ConcurrentHashMap<>();

    static {
        actions.put("chatsAction", ChatsAction.class);
        actions.put("chatAction", ChatAction.class);
        actions.put("loginAction", LoginAction.class);
        actions.put("logoutAction", LogoutAction.class);
        actions.put("registrationAction", RegistrationAction.class);
        actions.put("errorAction", ErrorAction.class);

        actionName.put("/","loginAction");
        actionName.put("/chat", "chatAction");
        actionName.put("/chats", "chatsAction");
        actionName.put("/login", "loginAction");
        actionName.put("/logout", "logoutAction");
        actionName.put("/register", "registrationAction");

    }

    private static String getActionName(String uri, String contextPath) {
        logger.debug("Processing action| uri: " + uri + "; contextPath: " + contextPath);
        if (!contextPath.equals("/water_war"))
            return "errorAction";
        String actionPath = uri.substring(contextPath.length());
        String action = actionName.get(actionPath);
        if (action == null)
            return "errorAction";
        return action;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String contextPath = httpRequest.getContextPath();
            String uri = httpRequest.getRequestURI();
            String actionName;

            logger.debug(String.format("Starting of processing of request for URI \"%s\"", uri));

            actionName = getActionName(uri, contextPath);

            logger.debug("ActionFromUriFilter Word: " + actionName);

            Class<Action> actionClass = (Class<Action>) actions.get(actionName);
            try {
                Action action = actionClass.newInstance();
                action.setName(actionName);
                request.setAttribute("action", action);
                chain.doFilter(request, response);
            } catch (
                    InstantiationException | IllegalAccessException |
                    NullPointerException e) {
                logger.error("It is impossible to create action handler object", e);
                httpRequest.setAttribute("error", String.format("Запрошенный адрес %s не может быть обработан сервером", uri));
                httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}

