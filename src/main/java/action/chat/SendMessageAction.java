package action.chat;

import dao.PersistException;
import entity.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.MessageService;
import validator.IncorrectFormDataException;
import validator.Validator;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendMessageAction extends ChatAction{
    private static Logger logger = LogManager.getLogger(SendMessageAction.class);
    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        try {
            Validator<Message> validator = ValidatorFactory.createValidator(Message.class);
            Message message = validator.validate(request);
            MessageService service = factory.getService(Message.class);
            service.persist(message);
            logger.info(String.format("User \"%s\" saved book with identity %d", getAuthorizedUser().getUsername(), message.getId()));
        } catch(IncorrectFormDataException e) {
            logger.warn(String.format("Incorrect data was found when user \"%s\" tried to save book", getAuthorizedUser().getUsername()), e);
        }
        return ;
    }
}
