package com.notes.util;

import com.notes.model.dto.ErrorSchemaDTO;
import com.notes.model.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler({
            HttpMessageNotReadableException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleHttpMessageNotReadable(Exception ex) {
        log.error(this.convertStackTraceToString(ex));
        ErrorSchemaDTO errorSchema = new ErrorSchemaDTO(Constant.ErrorCode.ERROR_BAD_REQUEST, "Bad request");
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, errorSchema, Arrays.asList(ex.getStackTrace()));
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
            NoSuchElementException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDTO> handleNoHandlerFoundException(Exception ex) {
        log.error(this.convertStackTraceToString(ex));
        ErrorSchemaDTO errorSchema = new ErrorSchemaDTO(Constant.ErrorCode.ERROR_NOT_FOUND, "Service or resource not found");
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, errorSchema, Arrays.asList(ex.getStackTrace()));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleAuthenticationException(Exception ex) {
        log.error(this.convertStackTraceToString(ex));
        ErrorSchemaDTO errorSchema = new ErrorSchemaDTO(Constant.ErrorCode.ERROR_BAD_REQUEST, "Authentication failed");
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, errorSchema, Arrays.asList(ex.getStackTrace()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDTO> handleOtherExceptions(Exception ex) {
        log.error(this.convertStackTraceToString(ex));
        ErrorSchemaDTO errorSchema = new ErrorSchemaDTO(Constant.ErrorCode.ERROR_INTERNAL_SERVER, "Internal server error");
        return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorSchema, Arrays.asList(ex.getStackTrace()));
    }

    /*
        AUTH
    */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponseDTO> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(this.convertStackTraceToString(ex));
        ErrorSchemaDTO errorSchema = new ErrorSchemaDTO(Constant.ErrorCode.ERROR_UNAUTHORIZED, "Access Denied");
        return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, errorSchema, Arrays.asList(ex.getStackTrace()));
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
