package pl.pragmatists.ddd.adm.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pragmatists.ddd.adm.domain.*;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueIdSequenceGenerator issueIdSequenceGenerator;

    @Autowired
    public IssueService(IssueRepository issueRepository, IssueIdSequenceGenerator issueIdSequenceGenerator) {
        this.issueRepository = issueRepository;
        this.issueIdSequenceGenerator = issueIdSequenceGenerator;
    }

    public Issue create(String issueName) {
        return issueRepository.save(new Issue(issueName, issueIdSequenceGenerator.nextId()));
    }

    public List<Issue> all() {
        return issueRepository.findAll();
    }

    public void addComment(String comment, String issueId) {
        Issue issue = issueRepository.findBy(IssueId.from(issueId));
        issue.addComment(comment);
        issueRepository.save(issue);
    }

    public Issue get(String issueId) {
        return issueRepository.findBy(IssueId.from(issueId));
    }

    public void update(String issueId, IssueStatus newStatus) {
        Issue issue = issueRepository.findBy(IssueId.from(issueId));
        issue.changeStatusTo(newStatus);
    }

}
