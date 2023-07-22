package controller.filter;


import action.*;
import action.authentication.LoginAction;
import action.authentication.LogoutAction;
import action.authentication.RegistrationAction;
import action.chat.SendMessageAction;
import action.chat.GetChatMessagesAction;
import action.chats.UserCreateChat;
import action.chats.UserNeedChatsAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@WebFilter(asyncSupported = true)
public class ActionFromUriFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(ActionFromUriFilter.class);

    private static final Map<String, Class<? extends Action>> actions = new ConcurrentHashMap<>();
    private static final Map<String, String> actionName = new ConcurrentHashMap<>();

    static {
        actions.put("/water/",LoginAction.class);
        actions.put("/water/chat", GetChatMessagesAction.class);
        actions.put("/water/chatcreate", UserCreateChat.class);
        actions.put("/water/chats", UserNeedChatsAction.class);
        actions.put("/water/login", LoginAction.class);
        actions.put("/water/logout", LogoutAction.class);
        actions.put("/water/message", SendMessageAction.class);
        actions.put("/water/register", RegistrationAction.class);
        actions.put("errorAction", ErrorAction.class);
    }

    private static String getActionName(String uri, String contextPath) {
        if (!contextPath.equals("/water_war"))
            return "errorAction";
        String action = "null";
        action = uri.substring(contextPath.length());
        logger.debug(action);
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

