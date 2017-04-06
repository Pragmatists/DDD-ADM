package pl.pragmatists.ddd.adm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pragmatists.ddd.adm.controller.json.IssueJson;
import pl.pragmatists.ddd.adm.controller.json.NewIssueCommentJson;
import pl.pragmatists.ddd.adm.controller.json.NewIssueJson;
import pl.pragmatists.ddd.adm.controller.json.UpdateIssueJson;
import pl.pragmatists.ddd.adm.model.Issue;
import pl.pragmatists.ddd.adm.model.IssueComment;
import pl.pragmatists.ddd.adm.service.IssueService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/issue")
public class IssueController {

    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @RequestMapping(value = "/{issueId}", method = GET)
    @ResponseBody
    public IssueJson get(@PathVariable String issueId) {
        Issue issue = issueService.get(issueId);
        return IssueJson.fromModel(issue);
    }

    @RequestMapping(method = GET)
    @ResponseBody
    public List<IssueJson> all() {
        return issueService.all().stream()
                .map(issue -> IssueJson.fromModel(issue))
                .collect(toList());
    }

    @RequestMapping(method = POST)
    @ResponseStatus(OK)
    public Long newIssue(@RequestBody NewIssueJson newIssueJson) {
        Issue createdIssue = issueService.create(newIssueJson.name);
        return createdIssue.getId();
    }

    @RequestMapping(value = "/{issueId}", method = PUT)
    @ResponseStatus(OK)
    public ResponseEntity<Void> update(@PathVariable Long issueId, @RequestBody UpdateIssueJson updateIssueJson) {
        try {
            issueService.update(issueId, updateIssueJson.status);
            return ok().build();
        } catch (RuntimeException ex) {
            return status(FORBIDDEN).build();
        }
    }

    @RequestMapping(value = "/{issueId}/comment", method = POST)
    @ResponseStatus(OK)
    public ResponseEntity<Long> newIssueComment(@PathVariable String issueId, @RequestBody NewIssueCommentJson newIssueCommentJson) {
        Optional<IssueComment> createdIssueComment = issueService.addComment(newIssueCommentJson.comment, issueId);
        return createdIssueComment
                .map(issueComment -> ok(issueComment.getId()))
                .orElse(status(FORBIDDEN).build());
    }


}
