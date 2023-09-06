package controller.listener;

import controller.filter.SecurityFilter;
import controller.servlet.http.HttpDispatcherServlet;
import dao.PersistException;
import dao.mysql.pool.MySqlConnectionPool;
import dao.pool.PollInit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(String.valueOf(HttpDispatcherServlet.class));

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            logger.debug("Started...");
            PollInit.init();
            SecurityFilter.setAdminMode();
            String configPath = "controller/config.properties";
            Properties properties = new Properties();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream in = classloader.getResourceAsStream(configPath);
            properties.load(in);
            Objects.requireNonNull(in).close();
            if (properties.getProperty("mode").equals("Admin"))
                SecurityFilter.setAdminMode();
            else
                SecurityFilter.setUserMode();
        } catch (PersistException | IOException e) {
            logger.error("It is impossible to initialize application", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        MySqlConnectionPool.getInstance().destroy();
    }
}