package action.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.DispatcherServlet;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class Sender {
    private static Logger logger = Logger.getLogger(String.valueOf(Sender.class));

    public static void sendObject(HttpServletResponse response, Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);
        logger.debug("Send JSON: " + json);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();
        writer.close();

        logger.debug(response.getHeaderNames());
    }
}
