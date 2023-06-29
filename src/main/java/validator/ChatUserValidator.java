package validator;

import action.parser.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import entity.Chat;
import entity.User;
import org.testng.internal.collections.Pair;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class ChatUserValidator implements Validator<Pair<Chat, User>> {
    @Override
    public Pair<Chat, User> validate(HttpServletRequest request) throws IncorrectFormDataException {
        JsonNode jsonNode = null;
        try {
            jsonNode = Parser.parseRequest(request);
        } catch (IOException e) {
            return null;
            // todo process request with no body
        }
        JsonNode chatIdNode = null;
        JsonNode userNameNode = null;
        if (jsonNode == null) {
            return null;
        }
        chatIdNode = jsonNode.get("chatId");
        userNameNode = jsonNode.get("username");
        if (chatIdNode == null || userNameNode == null) {
            return null;
        }
        Chat chat = new Chat();
        chat.setId(chatIdNode.asText());
        User user = new User();
        user.setUsername(userNameNode.asText());
        return new Pair<>(chat, user);
    }
}
