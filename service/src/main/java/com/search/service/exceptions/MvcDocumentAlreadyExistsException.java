package com.search.service.exceptions;


import com.search.service.DocumentAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class MvcDocumentAlreadyExistsException extends DocumentAlreadyExistsException {

    public MvcDocumentAlreadyExistsException(final String documentKey) {
        super(documentKey);
    }
}
