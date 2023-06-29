package validator;

import entity.Entity;

import javax.servlet.http.HttpServletRequest;

public interface Validator<Type> {
    Type validate(HttpServletRequest request) throws IncorrectFormDataException;
}
