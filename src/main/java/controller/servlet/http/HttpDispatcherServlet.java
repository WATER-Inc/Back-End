package controller.servlet.http;

import action.AbstractAction;
import action.http.HttpAction;
import action.ActionManager;
import action.ActionManagerFactory;
import action.sender.SenderManager;
import controller.servlet.Dispatcher;
import dao.PersistException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet(asyncSupported = true)
public class HttpDispatcherServlet extends Dispatcher<HttpServletRequest, HttpServletResponse> {
    private static final Logger logger = LogManager.getLogger(String.valueOf(HttpDispatcherServlet.class));



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

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException{
        AbstractAction action = (AbstractAction) request.getAttribute("action");
        ActionManager actionManager = null;
        try {
            actionManager = ActionManagerFactory.getManager(getFactory());
            actionManager.execute(action, request, response);
        } catch (PersistException e) {
            sendError(e, request, response);
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
    @Override
    protected HttpAction getAction(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        return (HttpAction) httpServletRequest.getAttribute("action");
    }

    @Override
    protected void sendError(Exception e, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        logger.error("It is impossible to process request", e);
        httpServletRequest.setAttribute("error", "Ошибка обработки данных");
        response.reset();
        try {
            SenderManager.sendObject(response, e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}

