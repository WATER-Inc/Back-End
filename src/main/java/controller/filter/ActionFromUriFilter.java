package controller.filter;


import action.ActionFactory;
import action.http.HttpAction;
import action.http.ErrorHttpAction;
import action.http.authentication.LoginHttpAction;
import action.http.authentication.LogoutHttpAction;
import action.http.authentication.RegistrationHttpAction;
import action.http.chat.AddUserToChatHttpAction;
import action.http.chat.longpolling.SendMessageHttpAction;
import action.http.chat.longpolling.GetChatMessagesHttpAction;
import action.http.chats.UserCreateChat;
import action.http.chats.UserNeedChatsHttpAction;
import action.http.common.FindUserByLoginHttpAction;
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

    private static String getActionName(String uri, String contextPath) {
        if (!contextPath.equals("/water_war"))
            return "errorAction";
        String action;
        action = uri.substring(contextPath.length());
        logger.debug(action);
        return action;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            if(httpRequest.getHeader("Upgrade") != null && httpRequest.getHeader("Upgrade").equals("websocket")) {
                System.out.println("Socket ");
                chain.doFilter(request, response);
            }
            else {
                String contextPath = httpRequest.getContextPath();
                String uri = httpRequest.getRequestURI();
                String actionName;
                logger.debug(String.format("Starting of processing of request for URI \"%s\"", uri));
                actionName = getActionName(uri, contextPath);
                Class<HttpAction> actionClass = (Class<HttpAction>) ActionFactory.getAction(actionName);//TODO maybe error
                logger.debug(actionClass);
                try {
                    HttpAction httpAction = actionClass.newInstance();
                    httpAction.setName(actionName);
                    request.setAttribute("action", httpAction);
                    chain.doFilter(request, response);
                } catch (
                        InstantiationException | IllegalAccessException |
                        NullPointerException e) {
                    logger.error("It is impossible to create action handler object", e);
                    httpRequest.setAttribute("error", String.format("Запрошенный адрес %s не может быть обработан сервером", uri));
                    httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
                }
            }
        } else {
            chain.doFilter(request, response);
            logger.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}

