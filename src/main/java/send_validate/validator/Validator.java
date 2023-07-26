package send_validate.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import send_validate.sender.SenderManager;

import javax.servlet.http.HttpServletRequest;

public abstract class Validator<OutType> {
    protected static Logger logger = LogManager.getLogger(String.valueOf(Validator.class));
    public abstract OutType validate(HttpServletRequest request) throws IncorrectFormDataException;
}
