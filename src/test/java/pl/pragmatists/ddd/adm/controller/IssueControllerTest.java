package pl.pragmatists.ddd.adm.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pragmatists.ddd.adm.Application;
import pl.pragmatists.ddd.adm.controller.json.*;
import pl.pragmatists.ddd.adm.model.IssueStatus;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pl.pragmatists.ddd.adm.model.IssueStatus.DONE;
import static pl.pragmatists.ddd.adm.model.IssueStatus.IN_PROGRESS;
import static pl.pragmatists.ddd.adm.model.IssueStatus.NEW;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {IntegrationTestConfiguration.class})
public class IssueControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void create_issue() {
        newIssue("Bug 01");

        List<IssueJson> issues = allIssues();

        assertThat(issues).contains(new IssueJson("Bug 01"));
    }

    @Test
    public void issue_is_in_open_status_after_creation() throws Exception {
        Long issueId = newIssue();

        IssueJson issue = getIssue(issueId);

        assertThat(issue.status).isEqualTo("NEW");
    }

    @Test
    public void change_issue_status_to_in_progress() {
        Long issueId = newIssue();

        updateIssueStatus(issueId, IN_PROGRESS);

        assertThat(getIssue(issueId).status).isEqualTo("IN_PROGRESS");
    }

    @Test
    public void cannot_change_status_to_done_from_new() {
        Long issueId = newIssue();

        ResponseEntity<Void> response = updateIssueStatus(issueId, DONE);

        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
        assertThat(getIssue(issueId).status).isEqualTo("NEW");
    }

    @Test
    public void cannot_change_status_to_new_from_done() {
        Long issueId = newIssue();
        updateIssueStatus(issueId, IN_PROGRESS);
        updateIssueStatus(issueId, DONE);


        ResponseEntity<Void> response = updateIssueStatus(issueId, NEW);

        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
        assertThat(getIssue(issueId).status).isEqualTo("DONE");
    }

    @Test
    public void add_comment_to_issue() {
        Long issueId = newIssue();

        commentIssue(issueId, "Comment text");
        commentIssue(issueId, "Next comment");

        assertThat(getIssue(issueId).getComments()).containsExactly(
                new IssueCommentJson("Comment text"),
                new IssueCommentJson("Next comment")
        );
    }

    @Test
    public void cannot_add_comment_when_issue_is_done() throws Exception {
        Long issueId = newIssue();
        updateIssueStatus(issueId, IN_PROGRESS);
        updateIssueStatus(issueId, DONE);

        ResponseEntity<Long> response = commentIssue(issueId, "Comment text");

        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
        assertThat(getIssue(issueId).comments).isEmpty();
    }

    private IssueJson getIssue(Long issueId) {
        return restTemplate.getForObject(format("/issue/%s", issueId), IssueJson.class);
    }

    private Long newIssue(String name) {
        return restTemplate.postForEntity("/issue", new NewIssueJson(name), Long.class).getBody();
    }

    private Long newIssue() {
        return newIssue("Some bug");
    }

    private ResponseEntity<Void> updateIssueStatus(Long issueId, IssueStatus status) {
        return put(format("/issue/%s", issueId), new UpdateIssueJson(status));
    }

    private List<IssueJson> allIssues() {
        return asList(restTemplate.getForObject("/issue", IssueJson[].class));
    }

    private ResponseEntity<Long> commentIssue(Long issueId, String comment) {
        return restTemplate.postForEntity(format("/issue/%s/comment", issueId), new NewIssueCommentJson(comment), Long.class);
    }

    public ResponseEntity<Void> put(String url, UpdateIssueJson requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(requestBody, headers), Void.class);
    }
}