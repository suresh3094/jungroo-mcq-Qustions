package com.jungroo.task.service.ServiceImpl;

import com.jungroo.task.constants.MessageCodes;
import com.jungroo.task.dto.AnswerDto;
import com.jungroo.task.dto.QuestionDto;
import com.jungroo.task.entity.Answer;
import com.jungroo.task.entity.QuestionOption;
import com.jungroo.task.entity.Question;
import com.jungroo.task.entity.User;
import com.jungroo.task.io.BaseResponse;
import com.jungroo.task.io.StatusMessage;
import com.jungroo.task.repository.OptionRepository;
import com.jungroo.task.repository.QuestionRepository;
import com.jungroo.task.repository.UserRepository;
import com.jungroo.task.service.QuestionService;
import com.jungroo.task.service.UserService;
import com.jungroo.task.util.CommonUtils;
import com.jungroo.task.util.RandomIdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    OptionRepository optionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public BaseResponse AddQuestion(List<QuestionDto> questionDto,Principal principal) throws Exception {
        String role=userService.getRolesByLoggedInUser(principal);
        if (role.equals("ContentCreator")) {
            for (QuestionDto questionDto1 : questionDto) {
                Question question = questionRepository.findByQuestionId(questionDto1.getQuestionId());
                if (CommonUtils.checkIsNullOrEmpty(question)) {
                    Question question1 = new Question();
                    question1.setQuestionId(RandomIdGenerator.generateRandomAplhaNumericString(6));
                    question1.setQuestion(questionDto1.getQuestion());
                    List<QuestionOption> options = new ArrayList<>();
                    if (!CommonUtils.checkIsNullOrEmpty(questionDto1.getOptions())) {
                        for (QuestionDto.Option option : questionDto1.getOptions()) {
                            QuestionOption options1 = new QuestionOption();
                            options1.setOptionId(RandomIdGenerator.generateRandomAplhaNumericString(5));
                            options1.setOption(option.getOption());
                            options1.setQuestion(question1);
                            options.add(options1);
                        }
                    }
                    question1.setOptions(options);
                    questionRepository.save(question1);
                }
            }
        }
        else {
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS_MSG)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_MSG)
                            .build())
                    .data("You have no access to add Question")
                    .build();
        }
        List<Question> questions=questionRepository.findAll();
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS_MSG)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_MSG)
                        .build())
                .data(questions)
                .build();
    }

    @Override
    public BaseResponse getQuestionAll() throws Exception {
        List<Question> questions=questionRepository.findAll();
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS_MSG)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_MSG)
                        .build())
                .data(questions)
                .build();
    }

    @Override
    public BaseResponse editQuestion(String questionId, QuestionDto questionDto,Principal principal) throws Exception {
        String role=userService.getRolesByLoggedInUser(principal);
        if(role.equals("ContentCreator")) {
            Question question = questionRepository.findByQuestionId(questionId);
            BeanUtils.copyProperties(questionDto, question, CommonUtils.getNullPropertyNames(questionDto));
            if (!CommonUtils.checkIsNullOrEmpty(questionDto.getOptions())) {
                optionRepository.deletePlayersId(questionId);
                List<QuestionOption> options = new ArrayList<>();
                for (QuestionDto.Option option : questionDto.getOptions()) {
                    QuestionOption options1 = new QuestionOption();
                    BeanUtils.copyProperties(option, options1);
                    options1.setOptionId(RandomIdGenerator.generateRandomAplhaNumericString(5));
                    options1.setQuestion(question);
                    options.add(options1);
                }
                question.setOptions(options);
            }
            questionRepository.save(question);
        }
        else {
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS_MSG)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_MSG)
                            .build())
                    .data("You have no access to Edit Question")
                    .build();
        }
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Updated Successfully")
                .build();
    }

    @Override
    public BaseResponse delete(String questionId,Principal principal) throws Exception {
        String role=userService.getRolesByLoggedInUser(principal);
        if (role.equals("ContentCreator")) {
            questionRepository.deleteById(questionId);
        }
        else {
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS_MSG)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_MSG)
                            .build())
                    .data("You have no access to delete Question")
                    .build();
        }
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(questionId+" "+"deleted user successfully")
                .build();
    }

    @Override
    public BaseResponse answer(String userId, List<AnswerDto> answerDto) throws Exception {
        User user=userRepository.findByuserId(userId);
        if (!CommonUtils.checkIsNullOrEmpty(user)) {
            List<Answer> answers = new ArrayList<>();
            for (AnswerDto answerDto1 : answerDto) {
                Answer answer = new Answer();
                answer.setAnswerId(RandomIdGenerator.generateRandomAplhaNumericString(6));
                answer.setQuestionId(answerDto1.getQuestionId());
                answer.setOptionId(answerDto1.getOptionId());
                answer.setUser(user);
                answers.add(answer);
                user.setAnswers(answers);
                userRepository.save(user);
            }
        }
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Response Submitted Successfully")
                .build();
    }
}
