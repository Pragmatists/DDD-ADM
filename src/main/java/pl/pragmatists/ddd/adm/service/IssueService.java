package pl.pragmatists.ddd.adm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pragmatists.ddd.adm.model.Issue;
import pl.pragmatists.ddd.adm.model.IssueComment;
import pl.pragmatists.ddd.adm.model.IssueStatus;
import pl.pragmatists.ddd.adm.repository.IssueCommentRepository;
import pl.pragmatists.ddd.adm.repository.IssueRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.valueOf;
import static pl.pragmatists.ddd.adm.model.IssueStatus.DONE;
import static pl.pragmatists.ddd.adm.model.IssueStatus.NEW;

@Service
@Transactional
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueCommentRepository issueCommentRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository, IssueCommentRepository issueCommentRepository) {
        this.issueRepository = issueRepository;
        this.issueCommentRepository = issueCommentRepository;
    }

    public Issue create(String issueName) {
        Issue newIssue = new Issue();
        newIssue.setName(issueName);
        newIssue.setStatus(NEW);
        return issueRepository.save(newIssue);
    }

    public List<Issue> all() {
        return issueRepository.findAll();
    }

    public Optional<IssueComment> addComment(String comment, String issueId) {
        Issue issue = issueRepository.findOne(valueOf(issueId));
        if (issue.getStatus() != DONE) {
            return Optional.of(issueCommentRepository.save(new IssueComment(comment, issue)));
        }
        return Optional.empty();
    }

    public Issue get(String issueId) {
        Issue issue = issueRepository.findOne(Long.valueOf(issueId));
        issue.setComments(issueCommentRepository.findByIssue_Id(issue.getId()));
        return issue;
    }

    public void update(Long issueId, IssueStatus newStatus) {
        Issue issue = issueRepository.findOne(issueId);
        if (issue.getStatus() == DONE && newStatus == NEW || issue.getStatus() == NEW && newStatus == DONE) {
            throw new RuntimeException(String.format("Cannot change issue status from %s to %s", issue.getStatus(), newStatus));
        }
        issue.setStatus(newStatus);
    }

}
