package com.notes.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    @JsonProperty(value = "error_schema")
    @JsonAlias(value = "error_schema")
    private ErrorSchemaDTO errorSchema;

    @JsonProperty(value = "output_schema")
    @JsonAlias(value = "output_schema")
    private Map<String, ?> outputSchema;

}
