package send_validate.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import serealization_deserealization.serialization.Serializer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class SenderManager {
    private static Logger logger = LogManager.getLogger(String.valueOf(SenderManager.class));
    private static Map<Object, Class<Object>> senders;
    static{

    }
    public static void sendObject(HttpServletResponse response, Object object) throws IOException {
        Serializer serializer = new Serializer();
        String json = serializer.serialize(object);
        logger.debug("Send JSON: " + json);
        PrintWriter out = response.getWriter();
        response.setHeader("Access-Control-Allow-Origin","http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods","GET, PUT, POST");
        response.setHeader("Access-Control-Allow-Headers","Content-Type, *");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.println(json);
        out.close();
    }
}
