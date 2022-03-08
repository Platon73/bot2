package ru.platon.bot2.entities.questionnaire;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Map;


@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
/* Опросник */
@Table(name = "questionnaire")
public class Questionnaire {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    /* название опросника */
    @Column(name = "title")
    private String title;
    /* вопрос и вариант ответа */
    @ElementCollection
    @CollectionTable(name = "questionnaire_qa",
            joinColumns = {@JoinColumn(name = "questions_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "name_key")
    @Column(name = "list_id_answer")
    private Map<Long, String> questionAndAnswer;
}
