package com.jungroo.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private String questionId;
    private String question;
    private List<Option> options;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Option{
        private String optionId;
        private String option;

    }
}
