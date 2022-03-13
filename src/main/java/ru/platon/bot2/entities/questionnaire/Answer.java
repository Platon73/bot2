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
/* Ответы */
@Table(name = "answer")
public class Answer {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "answer")
    private String answer;

    public Answer(Long id, String answer) {
        this.id = id;
        this.answer = answer;
    }
}
