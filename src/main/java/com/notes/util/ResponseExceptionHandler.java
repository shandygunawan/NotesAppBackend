package com.notes.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.hibernate.PropertyValueException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    final static Logger logger = Logger.getLogger(ResponseExceptionHandler.class);

    /*
        OVERRIDE
    */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage());

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("status", status);
        map.put("message", ex.getMessage());
        map.put("data", null);

        return new ResponseEntity<Object>(map, status);
    }

    /*
        CUSTOM EXCEPTIONS
    */


    /*
        OTHER EXCEPTIONS
    */
    @ExceptionHandler({
            PropertyValueException.class,
            NoSuchElementException.class,
            EmptyResultDataAccessException.class
    })
    protected ResponseEntity<Object> handleBadRequestException(Exception ex) {
        logger.error(this.convertStackTraceToString(ex));

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("message", ex.getMessage());
        map.put("data", null);

        return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
    }

    /*
        UTILS
    */
    private String convertStackTraceToString(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

}
