package ru.platon.bot2.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.platon.bot2.entities.questionnaire.CompletedQuestionnaire;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
/* шаблоны отчетов */
@Table(name = "user_tb")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
    private String name;
    /* Список заполненных опросов, которые прошел клиент */
    @OneToMany
    private List<CompletedQuestionnaire> listCompletedQuestionnaire;
    /* Статус Юзера */
    @Column
    private Status status;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", listCompletedQuestionnaire=" + listCompletedQuestionnaire +
                ", status=" + status +
                '}';
    }
}
