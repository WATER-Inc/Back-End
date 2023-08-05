package controller.servlet;

import action.Action;
import action.ActionManager;
import action.ActionManagerFactory;
import dao.PersistException;
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
import java.sql.SQLException;
import java.util.Map;
@WebServlet(asyncSupported = true)
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(String.valueOf(DispatcherServlet.class));

    public static ServiceFactory getFactory() throws PersistException {
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
        ActionManager actionManager = null;
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
            actionManager = ActionManagerFactory.getManager(getFactory());
            actionManager.execute(action, request, response);

        } catch (PersistException e) {
            logger.error("It is impossible to process request", e);
            request.setAttribute("error", "Ошибка обработки данных");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
        finally {
            if(actionManager != null) {
                try {
                    actionManager.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

