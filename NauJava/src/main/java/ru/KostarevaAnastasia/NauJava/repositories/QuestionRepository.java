package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.Question;


@RepositoryRestResource(path = "questions")
public interface QuestionRepository extends CrudRepository<Question, Long> {
}

