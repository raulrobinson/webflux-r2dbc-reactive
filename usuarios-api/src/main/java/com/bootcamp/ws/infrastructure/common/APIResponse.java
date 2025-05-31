package com.bootcamp.ws.infrastructure.common;

import com.bootcamp.ws.infrastructure.common.dto.ErrorDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class APIResponse {
    private int code;
    private String message;
    private String identifier;
    private String date;
    private Object data;
    private List<ErrorDTO> errors;
}
