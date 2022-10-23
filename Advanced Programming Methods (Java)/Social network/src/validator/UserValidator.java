package validator;

import exceptions.ValidationException;
import model.entities.User;

import static utils.Constants.*;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User user) throws ValidationException {
        String errors = "";
        if (user.getId() < 0)
            errors += ID_NOT_VALID;
        if (user.getFirstName().equals(""))
            errors += "First name is not valid.\n";
        if (user.getLastName().equals(""))
            errors += "Last name is not valid.\n";
        if (!errors.equals(""))
            throw new ValidationException(errors.substring(0, errors.length() - 1));
    }
}
