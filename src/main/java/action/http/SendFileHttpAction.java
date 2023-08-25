package action.http;

import controller.servlet.http.HttpDispatcherServlet;
import dao.PersistException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SendFileHttpAction extends HttpAction {
    private static final Logger logger = LogManager.getLogger(String.valueOf(HttpDispatcherServlet.class));
    final private static Integer BUFFER_SIZE = 4096;
    private static final Map<String,String> contentType = new HashMap<>();

    static{
        contentType.put("css","text/css");
        contentType.put("html","text/html; charset=utf-8");
        contentType.put("jpeg","image/jpeg");
        contentType.put("jpg","image/jpeg");
        contentType.put("js","text/javascript");
        contentType.put("json","application/json");
        contentType.put("svg","image/svg");
        contentType.put("png","image/jpeg");
    }
    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        String uri = request.getRequestURI();
        logger.info("SendFile Action for: " + uri);
        String fileType = uri.substring(uri.lastIndexOf('.')+1);

        File file = new File("../webapps/" +  uri);
        if(!file.exists()){
            // TODO file not found
            return;
        }
        try {
            logger.info("Sending File: " +  file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response.setContentType(contentType.get(fileType));
        try {
            FileInputStream  fileInputStream = new FileInputStream(file);
            ServletOutputStream out = response.getOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return;
    }
}
