package com.taxi.taxihailcore.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    @JsonProperty("status")
    private int status;
    @JsonProperty("message")
    private String message;
}
