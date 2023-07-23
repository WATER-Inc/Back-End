package validator;

import action.parser.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import entity.Chat;
import entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class RoleValidator implements Validator<Role> {
    @Override
    public Role validate(HttpServletRequest request) throws IncorrectFormDataException {
        JsonNode jsonNode = null;
        try {
            jsonNode = Parser.parseRequest(request);
        } catch (IOException e) {
            return null;
            // todo process request with no body
        }
        JsonNode titleNode = null;
        if (jsonNode == null) {
            return null;
        }
        titleNode = jsonNode.get("roleTitle");
        Role role = new Role();
        if(titleNode != null)
            role.setTitle(titleNode.asText());
        else return null;
        return role;
    }
}
