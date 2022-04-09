package com.notes.util;

import com.notes.model.dto.ErrorSchemaDTO;
import com.notes.model.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class ResponseHandler {

    public static ResponseEntity<ResponseDTO> generateResponse(HttpStatus httpStatus, ErrorSchemaDTO errorSchemaDTO, Object responseObj) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setErrorSchema(errorSchemaDTO);

        if(responseObj != null) {
            responseDTO.setOutputSchema(Collections.singletonMap("data", responseObj));
        }

        return new ResponseEntity<>(responseDTO, httpStatus);
    }
}
