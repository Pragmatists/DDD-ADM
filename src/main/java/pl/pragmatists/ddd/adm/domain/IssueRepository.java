package pl.pragmatists.ddd.adm.domain;

import org.springframework.data.repository.CrudRepository;
import pl.pragmatists.ddd.adm.domain.Issue;

import java.util.List;

public interface IssueRepository extends CrudRepository<Issue, Long> {

    List<Issue> findAll();
}
