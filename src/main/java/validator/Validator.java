package validator;

import entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public abstract class Validator<Type extends Entity> {
    protected static Logger logger = LogManager.getLogger(Validator.class);
    public abstract Type validate(HttpServletRequest request) throws IncorrectFormDataException;
}
