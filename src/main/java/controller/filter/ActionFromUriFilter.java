package controller.filter;


import action.*;
import action.authentication.LoginAction;
import action.authentication.LogoutAction;
import action.authentication.RegistrationAction;
import action.chat.SendMessageAction;
import action.chat.GetChatMessagesAction;
import action.chats.UserNeedChatsAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionFromUriFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(ActionFromUriFilter.class);

    private static final Map<String, Class<? extends Action>> actions = new ConcurrentHashMap<>();
    private static final Map<String, String> actionName = new ConcurrentHashMap<>();

    static {
        actions.put("chatsAction", UserNeedChatsAction.class);
        actions.put("chatAction", GetChatMessagesAction.class);
        actions.put("loginAction", LoginAction.class);
        actions.put("logoutAction", LogoutAction.class);
        actions.put("messageAction", SendMessageAction.class);
        actions.put("registrationAction", RegistrationAction.class);
        actions.put("errorAction", ErrorAction.class);

        actionName.put("/","loginAction");
        actionName.put("/chat", "chatAction");
        actionName.put("/chats", "chatsAction");
        actionName.put("/login", "loginAction");
        actionName.put("/logout", "logoutAction");
        actionName.put("/message","messageAction");
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
            logger.info("Session Id:" + httpRequest.getSession().getId());
            logger.debug(String.format("Starting of processing of request for URI \"%s\"", uri));

            actionName = getActionName(uri, contextPath);

            logger.debug("ActionFromUriFilter Word: " + actionName);

            Class<Action> actionClass = (Class<Action>) actions.get(actionName);

            logger.debug(actionClass);
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
