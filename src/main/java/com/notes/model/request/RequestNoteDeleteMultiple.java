package com.notes.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestNoteDeleteMultiple {

    @JsonProperty(value = "ids", required = true)
    private List<UUID> ids;
}
