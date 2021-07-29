package com.jungroo.task.repository;

import com.jungroo.task.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question,String> {
    Question findByQuestionId(String questionId);
}
