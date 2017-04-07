package pl.pragmatists.ddd.adm.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pragmatists.ddd.adm.domain.Issue;
import pl.pragmatists.ddd.adm.domain.IssueRepository;
import pl.pragmatists.ddd.adm.domain.IssueStatus;

import javax.transaction.Transactional;
import java.util.List;

import static java.lang.Long.valueOf;

@Service
@Transactional
public class IssueService {

    private final IssueRepository issueRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Issue create(String issueName) {
        return issueRepository.save(new Issue(issueName));
    }

    public List<Issue> all() {
        return issueRepository.findAll();
    }

    public void addComment(String comment, String issueId) {
        Issue issue = issueRepository.findOne(valueOf(issueId));
        issue.addComment(comment);
        issueRepository.save(issue);
    }

    public Issue get(String issueId) {
        return issueRepository.findOne(Long.valueOf(issueId));
    }

    public void update(Long issueId, IssueStatus newStatus) {
        Issue issue = issueRepository.findOne(issueId);
        issue.changeStatusTo(newStatus);
    }

}
