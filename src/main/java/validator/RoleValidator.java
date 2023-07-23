package validator;

import entity.Role;

import javax.servlet.http.HttpServletRequest;

public class RoleValidator implements Validator<Role> {
    @Override
    public Role validate(HttpServletRequest request) throws IncorrectFormDataException {
        Role role = new Role();
        String parameter = request.getParameter("roleId");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                role.setId(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("roleId", parameter);
            }
        }
        parameter = request.getParameter("roleTitle");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                role.setTitle(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("roleTitle", parameter);
            }
        }
        return role;
    }
}
