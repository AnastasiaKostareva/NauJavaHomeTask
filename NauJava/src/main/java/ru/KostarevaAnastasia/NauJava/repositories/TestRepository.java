package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.Test;

@RepositoryRestResource(path = "tests")
public interface TestRepository extends CrudRepository<Test, Long> {
}