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
/* заполненный опросник */
@Table(name = "completed_questionnaire")
public class CompletedQuestionnaire {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
    private String title;
    @ElementCollection
    @CollectionTable(name = "completed_questionnaire_QA",
            joinColumns = {@JoinColumn(name = "questions_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "name_key")
    @Column(name = "list_id_answer")
    private Map<Long, Long> questionAndAnswer;

}
