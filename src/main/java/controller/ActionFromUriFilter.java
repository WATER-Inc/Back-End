package controller;


import action.Action;
import action.LoginAction;
import action.LogoutAction;
import action.MainAction;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionFromUriFilter implements Filter {
    private static Logger logger = Logger.getLogger(ActionFromUriFilter.class);

    private static Map<String, Class<? extends Action>> actions = new ConcurrentHashMap<>();

    static {
        actions.put("/", MainAction.class);
        actions.put("/index", MainAction.class);
        actions.put("/login", LoginAction.class);
        actions.put("/logout", LogoutAction.class);
//
//        actions.put("/profile/edit", ProfileEditAction.class);
//        actions.put("/profile/save", ProfileSaveAction.class);
//
//        actions.put("/reader/list", ReaderListAction.class);
//        actions.put("/reader/edit", ReaderEditAction.class);
//        actions.put("/reader/save", ReaderSaveAction.class);
//        actions.put("/reader/delete", ReaderDeleteAction.class);
//
//        actions.put("/user/list", UserListAction.class);
//        actions.put("/user/edit", UserEditAction.class);
//        actions.put("/user/save", UserSaveAction.class);
//        actions.put("/user/delete", UserDeleteAction.class);
//
//        actions.put("/author/list", AuthorListAction.class);
//        actions.put("/author/edit", AuthorEditAction.class);
//        actions.put("/author/save", AuthorSaveAction.class);
//        actions.put("/author/delete", AuthorDeleteAction.class);
//
//        actions.put("/author/book/list", BookListAction.class);
//        actions.put("/author/book/edit", BookEditAction.class);
//        actions.put("/author/book/save", BookSaveAction.class);
//        actions.put("/author/book/delete", BookDeleteAction.class);
//
//        actions.put("/search/book/form", SearchBookFormAction.class);
//        actions.put("/search/book/result", SearchBookResultAction.class);
//        actions.put("/author/book/usages", BookUsageListAction.class);
//
//        actions.put("/search/reader/form", SearchReaderFormAction.class);
//        actions.put("/search/reader/result", SearchReaderResultAction.class);
//        actions.put("/reader/usages", ReaderUsageListAction.class);
//
//        actions.put("/author/book/deliver", DeliverBookAction.class);
//        actions.put("/author/book/return", ReturnBookAction.class);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            String contextPath = httpRequest.getContextPath();
            String uri = httpRequest.getRequestURI();
            logger.debug(String.format("Starting of processing of request for URI \"%s\"", uri));
            int beginAction = contextPath.length();
            int endAction = uri.lastIndexOf('.');
            String actionName;
            if(endAction >= 0) {
                actionName = uri.substring(beginAction, endAction);
            } else {
                actionName = uri.substring(beginAction);
            }
            logger.debug("ActionFromUriFilter Word!!!");
            logger.debug(actionName);
            Class<? extends Action> actionClass = actions.get(actionName);
            try {
                Action action = actionClass.newInstance();
                action.setName(actionName);
                httpRequest.setAttribute("action", action);
                chain.doFilter(request, response);
            } catch (InstantiationException | IllegalAccessException | NullPointerException e) {
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
    public void destroy() {}
}

