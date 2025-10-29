package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTestId;
import ru.KostarevaAnastasia.NauJava.repositories.custom.CustomQuestionToTestRepository;

import java.util.List;

@RepositoryRestResource(path = "questions-to-tests")
public interface QuestionToTestRepository
        extends CrudRepository<QuestionToTest, Long> {

    List<QuestionToTest> findByTestIdAndSortingOrderBetween(Long testId, Integer minOrder, Integer maxOrder);
}