package com.socialnetwork.domain.validators;


import com.socialnetwork.domain.entities.User;
import com.socialnetwork.domain.exceptions.ValidationException;

import static com.socialnetwork.utils.Constants.ID_NOT_VALID;

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
        if (user.getEmail().equals(""))  // TODO e-mail validation
            errors += "Email is not valid.\n";
        if (!errors.equals(""))
            throw new ValidationException(errors.substring(0, errors.length() - 1));
    }
}
