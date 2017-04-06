package pl.pragmatists.ddd.adm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pragmatists.ddd.adm.model.Issue;

import java.util.List;

public interface IssueRepository extends CrudRepository<Issue, Long> {

    List<Issue> findAll();
}
