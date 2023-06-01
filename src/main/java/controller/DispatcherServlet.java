package controller;

import action.Action;
import action.ActionManager;
import action.ActionManagerFactory;
import dao.PersistException;
import dao.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "Servlet", value = "/Servlet")
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(String.valueOf(DispatcherServlet.class));
    public static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/water?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "20November3;5.-65@1234";
    public static final int DB_POOL_START_SIZE = 100;
    public static final int DB_POOL_MAX_SIZE = 1000;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 100;

    public DispatcherServlet() {
        super();
        init();
    }

    public void init() {
        try {
            logger.debug("Started...");
            ConnectionPool.getInstance().init(DB_DRIVER_CLASS, DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
        } catch (PersistException e) {
            logger.error("It is impossible to initialize application", e);
            destroy();
        }
    }

    public ServiceFactory getFactory() throws PersistException {
        return new ServiceFactory();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // TODO differentiate recourse request and data request
        logger.debug("Get url: " + request.getRequestURI());
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Post url: " + request.getRequestURI());
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Action action = (Action) request.getAttribute("action");
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> attributes = (Map<String, Object>) session.getAttribute("redirectedData");
                if (attributes != null) {
                    for (String key : attributes.keySet()) {
                        request.setAttribute(key, attributes.get(key));
                    }
                    session.removeAttribute("redirectedData");
                }
            }
            logger.debug("Starting action execution");
            ActionManager actionManager = ActionManagerFactory.getManager(getFactory());
            actionManager.execute(action, request, response);
            actionManager.close();

        } catch (PersistException e) {
            logger.error("It is impossible to process request", e);
            request.setAttribute("error", "Ошибка обработки данных");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}

