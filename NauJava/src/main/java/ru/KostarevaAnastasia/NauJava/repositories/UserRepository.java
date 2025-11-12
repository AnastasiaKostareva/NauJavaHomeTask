package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.User;

@RepositoryRestResource(path = "users")
/**
 *  Репозиторий для сущности User
 */
public interface UserRepository extends CrudRepository<User, Long> {
}