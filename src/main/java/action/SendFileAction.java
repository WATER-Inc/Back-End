package action;

import controller.DispatcherServlet;
import dao.PersistException;
import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SendFileAction extends Action{
    private static Logger logger = Logger.getLogger(String.valueOf(DispatcherServlet.class));
    final private static Integer BUFFER_SIZE = 4096;
    private static Map<String,String> contentType = new HashMap<>();

    static{
        contentType.put("css","text/css");
        contentType.put("html","text/html; charset=utf-8");
        contentType.put("jpeg","image/jpeg");
        contentType.put("jpg","image/jpeg");
        contentType.put("js","text/javascript");
        contentType.put("json","application/json");
        contentType.put("svg","image/svg+xml");
        contentType.put("png","image/jpeg");
    }
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        String uri = request.getRequestURI();
        logger.info("SendFile Action for: " + uri);
        String fileType = uri.substring(uri.indexOf('.')+1);

        File file = new File("../webapps/" +  uri);
        if(!file.exists()){
            // TODO file not found
            return new Forward("/error");
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
        return null;
    }
}
