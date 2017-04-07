package pl.pragmatists.ddd.adm.controller.json;

import pl.pragmatists.ddd.adm.model.IssueStatus;
import pl.pragmatists.ddd.adm.model.Issue;
import pl.pragmatists.ddd.adm.model.IssueComment;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class IssueJson {

    public String name;
    public List<IssueCommentJson> comments;
    public String status;

    public IssueJson() { }

    public IssueJson(String name, IssueStatus status, List<IssueComment> comments) {
        this.name = name;
        this.status = status.toString();
        this.comments = comments.stream()
                .map(comment -> IssueCommentJson.fromModel(comment))
                .collect(toList());
    }

    public IssueJson(String name) {
        this.name = name;
    }

    public static IssueJson fromModel(Issue issue) {
        return new IssueJson(issue.getName(), issue.getStatus(), issue.getComments());
    }

    public List<IssueCommentJson> getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueJson issueJson = (IssueJson) o;

        return name != null ? name.equals(issueJson.name) : issueJson.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
