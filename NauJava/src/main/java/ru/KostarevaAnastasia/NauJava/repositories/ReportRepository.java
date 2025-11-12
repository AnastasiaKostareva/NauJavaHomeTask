package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.Report;

/**
 *Репозиторий для сущности Report
 */
@RepositoryRestResource(path = "reports")
public interface ReportRepository extends CrudRepository<Report, Long>  {

}
