package ru.platon.bot2.entities.questionnaire;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
/* Вопросы */
@Table(name = "question")
public class Question {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    /* сам вопрос */
    @Column(name = "question")
    private String question;
}
