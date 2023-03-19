package com.socialnetwork.domain.validators;


import com.socialnetwork.domain.exceptions.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
