package com.mypractice.content.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ErrorDto {

    private final String code;
    private final String description;
    private final List<ErrorDetail> details;

    @AllArgsConstructor
    @Getter
    public static class ErrorDetail {
        private final String name;
        private final String description;
    }

}
