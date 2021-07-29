package com.jungroo.task.service;

import com.jungroo.task.dto.AnswerDto;
import com.jungroo.task.dto.QuestionDto;
import com.jungroo.task.io.BaseResponse;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface QuestionService {
    BaseResponse AddQuestion(List<QuestionDto> questionDto, Principal principal) throws Exception;
    BaseResponse getQuestionAll()throws Exception;
    BaseResponse editQuestion(String questionId,QuestionDto questionDto,Principal principal) throws Exception;
    BaseResponse delete(String questionId,Principal principal)throws Exception;
    BaseResponse answer(String userId,List<AnswerDto> answerDto) throws Exception;
}
