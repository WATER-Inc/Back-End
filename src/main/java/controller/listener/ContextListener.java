package controller.listener;

import controller.servlet.http.HttpDispatcherServlet;
import dao.PersistException;
import dao.mysql.pool.MySqlConnectionPool;
import dao.pool.PollInit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(String.valueOf(HttpDispatcherServlet.class));

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            logger.debug("Started...");
            PollInit.init();
        } catch (PersistException e) {
            logger.error("It is impossible to initialize application", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        MySqlConnectionPool.getInstance().destroy();
    }
}