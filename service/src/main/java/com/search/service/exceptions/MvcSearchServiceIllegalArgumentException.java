package com.search.service.exceptions;

import com.search.service.SearchServiceIllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MvcSearchServiceIllegalArgumentException extends SearchServiceIllegalArgumentException {

    public MvcSearchServiceIllegalArgumentException(final String message) {
        super(message);
    }
}
