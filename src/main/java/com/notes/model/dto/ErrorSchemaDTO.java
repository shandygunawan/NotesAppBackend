package com.notes.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorSchemaDTO {

    @JsonProperty(value = "error_code")
    @JsonAlias(value = "error_code")
    private String errorCode;

    @JsonProperty(value = "error_message")
    @JsonAlias(value = "error_message")
    private String errorMessage;
}
