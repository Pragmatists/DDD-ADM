package pl.pragmatists.ddd.adm.domain;

import java.util.List;

public interface IssueRepository {

    List<Issue> findAll();

    Issue save(Issue issue);

    Issue findBy(IssueId issueId);
}
