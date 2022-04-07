package com.notes.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntityHandler {

    public static ResponseEntity<Object> generateResponse(HttpStatus httpStatus, String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("status", httpStatus);
        map.put("message", message);
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map, httpStatus);
    }
}
