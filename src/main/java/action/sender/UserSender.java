package action.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserSender<T> {
    public static void sendUser(HttpServletResponse response, User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        System.out.println(json);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
