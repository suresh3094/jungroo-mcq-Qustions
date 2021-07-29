package com.jungroo.task.controller;

import com.jungroo.task.dto.AnswerDto;
import com.jungroo.task.dto.QuestionDto;
import com.jungroo.task.io.BaseResponse;
import com.jungroo.task.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class QuestionController {
@Autowired
QuestionService question;


    @PostMapping("/createQuestion")
    public ResponseEntity<BaseResponse> createQuestion(@RequestBody List<QuestionDto> questionDto, Principal principal) throws Exception{
        BaseResponse baseResponse=question.AddQuestion(questionDto,principal);
        return ResponseEntity.ok(baseResponse);
    }
    @GetMapping("/getQuestionAll")
    public ResponseEntity<BaseResponse> getQuestionAll()throws Exception{
        BaseResponse baseResponse=question.getQuestionAll();
        return ResponseEntity.ok(baseResponse);
    }

    @PutMapping("/editQuestion/{questionId}")
    public ResponseEntity<BaseResponse> editQuestion(@PathVariable String questionId,@RequestBody QuestionDto questionDto,Principal principal) throws Exception{
        BaseResponse baseResponse=question.editQuestion(questionId,questionDto,principal);
        return ResponseEntity.ok(baseResponse);
    }
    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<BaseResponse> deleteQuestion(@PathVariable String questionId,Principal principal) throws Exception{
        BaseResponse baseResponse=question.delete(questionId,principal);
        return ResponseEntity.ok(baseResponse);
    }
    @PostMapping("/answer/{userId}")
    public ResponseEntity<BaseResponse> answer(@PathVariable String userId, @RequestBody List<AnswerDto> answerDto) throws Exception {
        BaseResponse response = question.answer(userId,answerDto);
        return ResponseEntity.ok(response);
    }
}
