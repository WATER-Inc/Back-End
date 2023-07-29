package validator;

import action.parser.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import entity.User;
import entity.auxiliary.ChatLink;
import entity.auxiliary.PreChatLink;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PreChatLinkValidator extends Validator<PreChatLink> {
    @Override
    public PreChatLink validate(HttpServletRequest request) throws IncorrectFormDataException {
        JsonNode jsonNode = null;
        try {
            jsonNode = Parser.parseRequest(request);
        } catch (IOException e) {
            return null;
            // todo process request with no body
        }
        JsonNode chatIdNode = null;
        JsonNode usernameNode = null;
        JsonNode roleNameNode = null;
        JsonNode inviterNameNode = null;
        if (jsonNode != null) {
            chatIdNode = jsonNode.get("chatId");
            usernameNode = jsonNode.get("username");
            roleNameNode = jsonNode.get("roleTitle");
            inviterNameNode = jsonNode.get("inviter");
        }
        if(chatIdNode == null || usernameNode == null || roleNameNode == null)
            return null;
        PreChatLink preChatLink = new PreChatLink();
        preChatLink.setChatId(chatIdNode.asText());
        preChatLink.setUsername(usernameNode.asText());
        preChatLink.setRoleName(roleNameNode.asText());
        if(inviterNameNode != null)
            preChatLink.setInviterName(inviterNameNode.asText());
        else{
            logger.info("No one inviter");
        }
        return preChatLink;
    }
}

