package validator;

import entity.Chat;

import javax.servlet.http.HttpServletRequest;

public class ChatValidator implements Validator<Chat> {
    @Override
    public Chat validate(HttpServletRequest request) throws IncorrectFormDataException {
        Chat chat = new Chat();
        String parameter = request.getParameter("id");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                chat.setId(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("id", parameter);
            }
        }
        parameter = request.getParameter("name");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                chat.setName(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("name", parameter);
            }
        }
        return chat;
    }
}
