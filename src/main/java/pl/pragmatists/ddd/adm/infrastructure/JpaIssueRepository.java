package pl.pragmatists.ddd.adm.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.pragmatists.ddd.adm.domain.Issue;
import pl.pragmatists.ddd.adm.domain.IssueId;
import pl.pragmatists.ddd.adm.domain.IssueRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class JpaIssueRepository implements IssueRepository {

    private final CrudIssueRepository crudIssueRepository;

    @Autowired
    public JpaIssueRepository(CrudIssueRepository crudIssueRepository) {
        this.crudIssueRepository = crudIssueRepository;
    }

    @Override
    public List<Issue> findAll() {
        List<Issue> issues = new ArrayList<>();
        crudIssueRepository.findAll().forEach(issue -> issues.add(issue));
        return issues;
    }

    @Override
    public Issue save(Issue issue) {
        return crudIssueRepository.save(issue);
    }

    @Override
    public Issue findBy(IssueId issueId) {
        return crudIssueRepository.findOne(issueId);
    }
}
