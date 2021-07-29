package com.jungroo.task.repository;

import com.jungroo.task.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface OptionRepository extends JpaRepository<QuestionOption,String> {

    @Transactional
    @Modifying
    @Query(value = "delete from jungroo.choice_details where question_id=:question_id",nativeQuery = true)
    int deletePlayersId(String question_id);
}
