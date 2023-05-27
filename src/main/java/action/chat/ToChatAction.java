package action.chat;

import action.Action;
import dao.PersistException;
import entity.Message;
import org.apache.log4j.Logger;
import service.MessageService;
import validator.IncorrectFormDataException;
import validator.Validator;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToChatAction extends ChatAction{
    private static Logger logger = Logger.getLogger(SendMessageAction.class);
    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        Action.Forward forward = new Action.Forward("/chats/chat");
        try {
            Validator<Message> validator = ValidatorFactory.createValidator(Message.class);
            Message message = validator.validate(request);
            MessageService service = factory.getService(Message.class);
            service.persist(message);
            forward.getAttributes().put("identity", message.getId());
            forward.getAttributes().put("message", "Данные сообщения успешно сохранены");
            logger.info(String.format("User \"%s\" saved book with identity %d", getAuthorizedUser().getUsername(), message.getId()));
        } catch(IncorrectFormDataException e) {
            forward.getAttributes().put("message", "Были обнаружены некорректные данные");
            logger.warn(String.format("Incorrect data was found when user \"%s\" tried to save book", getAuthorizedUser().getUsername()), e);
        }
        return forward;
    }
}

