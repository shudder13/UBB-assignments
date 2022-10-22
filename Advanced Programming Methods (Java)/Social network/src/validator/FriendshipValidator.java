package validator;

import exceptions.ValidationException;
import model.Friendship;

import static utils.Constants.ID_NOT_VALID;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship friendship) throws ValidationException {
        String errors = "";
        if (friendship.getId() < 0)
            errors += ID_NOT_VALID;
        // TODO think if user must also be validated
        if (!errors.equals(""))
            throw new ValidationException(errors.substring(0, errors.length() - 1));
    }
}