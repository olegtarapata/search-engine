package com.search.service.app;

import com.search.service.DocumentAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class SearchServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DocumentAlreadyExistsException.class)
    public ExceptionRepresentation handleDocumentAlreadyExistException(DocumentAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.FOUND, request);
    }


}
