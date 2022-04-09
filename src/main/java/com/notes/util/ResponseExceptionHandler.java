package com.notes.util;

import com.notes.model.dto.ErrorSchemaDTO;
import com.notes.model.dto.ResponseDTO;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ResponseExceptionHandler {

    final static Logger logger = Logger.getLogger(ResponseExceptionHandler.class);

    @ExceptionHandler({
            HttpMessageNotReadableException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        logger.error(this.convertStackTraceToString(ex));
        ErrorSchemaDTO errorSchema = new ErrorSchemaDTO(Constant.ErrorCode.ERROR_BAD_REQUEST, "Bad request");
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, errorSchema, Arrays.asList(ex.getStackTrace()));
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
            NoSuchElementException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDTO> handleNoHandlerFoundException(Exception ex) {
        logger.error(this.convertStackTraceToString(ex));
        ErrorSchemaDTO errorSchema = new ErrorSchemaDTO(Constant.ErrorCode.ERROR_NOT_FOUND, "Service or resource not found");
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, errorSchema, Arrays.asList(ex.getStackTrace()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDTO> handleOtherExceptions(Exception ex) {
        logger.error(this.convertStackTraceToString(ex));
        ErrorSchemaDTO errorSchema = new ErrorSchemaDTO(Constant.ErrorCode.ERROR_INTERNAL_SERVER, "Internal server error");
        return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorSchema, Arrays.asList(ex.getStackTrace()));
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
