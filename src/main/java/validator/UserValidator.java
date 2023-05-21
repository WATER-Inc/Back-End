package validator;

import entity.User;

import javax.servlet.http.HttpServletRequest;

public class UserValidator implements Validator<User> {
    @Override
    public User validate(HttpServletRequest request) throws IncorrectFormDataException {
        User user = new User();
        String parameter = request.getParameter("id");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                user.setId(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("id", parameter);
            }
        }
        parameter = request.getParameter("username");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                user.setUsername(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("username", parameter);
            }
        }
        parameter = request.getParameter("passwordHash");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                user.setPasswordHash(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("passwordHash", parameter);
            }
        }
        return user;
    }
}
