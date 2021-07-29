package com.jungroo.task.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "choice_details")
public class QuestionOption {

    @Id
    @Column(name = "optionId")
    private String optionId;

    @Column(name = "option")
    private String option;

    @CreationTimestamp
    @Column(name = "createdOn")
    private LocalDateTime createdOn;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionId")
    @JsonBackReference
    private Question question;
}
