package action.common;

import send_validate.sender.SenderManager;
import dao.PersistException;
import entity.User;
import service.UserService;
import send_validate.validator.IncorrectFormDataException;
import send_validate.validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class FindUserByLoginAction extends CommonAction{
    public FindUserByLoginAction() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        User user = null;
        try {
            user = (User) Objects.requireNonNull(ValidatorFactory.createValidator(User.class)).validate(request);
            if(user == null)
                throw new IncorrectFormDataException("No one chat in request", "Error");
        } catch (IncorrectFormDataException e) {
            throw new PersistException(e);
        }
        UserService Uservice = factory.getService(User.class);
        user = Uservice.getByUsername(user.getUsername());
        try {
            SenderManager.sendObject(response, user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
