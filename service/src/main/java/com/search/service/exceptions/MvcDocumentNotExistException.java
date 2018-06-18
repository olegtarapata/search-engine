package com.search.service.exceptions;

import com.search.service.DocumentNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MvcDocumentNotExistException extends DocumentNotExistException {

    public MvcDocumentNotExistException(final String documentKey) {
        super(documentKey);
    }
}
