package pl.pragmatists.ddd.adm.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.pragmatists.ddd.adm.domain.IssueId;
import pl.pragmatists.ddd.adm.domain.IssueIdSequenceGenerator;

@Component
public class JpaIssueIdSequenceGenerator implements IssueIdSequenceGenerator {

    private final CrudIssueRepository issueRepository;

    @Autowired
    public JpaIssueIdSequenceGenerator(CrudIssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public IssueId nextId() {
        IssueId currentId = issueRepository.findTopById();
        return currentId == null ? IssueId.from("1") : currentId.next();
    }
}
