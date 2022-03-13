package ru.platon.bot2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.platon.bot2.entities.questionnaire.CompletedQuestionnaire;

public interface CompletedQuestionnaireRepository extends JpaRepository<CompletedQuestionnaire, Long> {
}
