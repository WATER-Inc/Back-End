package validator;

import entity.Chat;
import entity.Message;
import entity.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class MessageValidator implements Validator<Message> {
    @Override
    public Message validate(HttpServletRequest request) throws IncorrectFormDataException {
        Message message = new Message();
        String parameter = request.getParameter("id");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                message.setId(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("id", parameter);
            }
        }
        parameter = request.getParameter("sender");
        if (parameter != null && !parameter.isEmpty()) {
            User user = new User();
            try {
                user.setId(parameter);
                message.setSender(user);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("sender", parameter);
            }
        }
        parameter = request.getParameter("chat");
        if (parameter != null && !parameter.isEmpty()) {
            Chat chat = new Chat();
            try {
                chat.setId(parameter);
                message.setChat(chat);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("chat", parameter);
            }
        }
        parameter = request.getParameter("content");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                message.setContent(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("content", parameter);
            }
        }
        parameter = request.getParameter("date");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                message.setDate(Date.valueOf(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("date", parameter);
            }
        }
        return message;
    }
}
