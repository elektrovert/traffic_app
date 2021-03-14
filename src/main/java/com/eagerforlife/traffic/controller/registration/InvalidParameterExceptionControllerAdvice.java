package com.eagerforlife.traffic.controller.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import java.security.InvalidParameterException;
import java.util.Map;

@ControllerAdvice
public class InvalidParameterExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @Autowired
    private DefaultErrorAttributes defaultErrorAttributes;

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleInvalidParameterExceptionControllerAdvice(final RuntimeException e, final WebRequest request) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value(), RequestAttributes.SCOPE_REQUEST);
        final Map<String, Object> errorAttributes = defaultErrorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());

        return handleExceptionInternal(e, errorAttributes, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
