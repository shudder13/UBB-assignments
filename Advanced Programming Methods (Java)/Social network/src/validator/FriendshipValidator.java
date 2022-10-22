package validator;

import exceptions.ValidationException;
import model.Friendship;

import static utils.Constants.ID_NOT_VALID;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship friendship) throws ValidationException {
        if (friendship.getId() < 0)
            throw new ValidationException(ID_NOT_VALID.substring(0, ID_NOT_VALID.length() - 1));
    }
}