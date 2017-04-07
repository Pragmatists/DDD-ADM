package pl.pragmatists.ddd.adm.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static javax.persistence.GenerationType.AUTO;
import static pl.pragmatists.ddd.adm.model.IssueStatus.DONE;
import static pl.pragmatists.ddd.adm.model.IssueStatus.NEW;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    private IssueStatus status;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<IssueComment> comments;

    @SuppressWarnings("unused") // for hibernate
    private Issue() {
    }

    public Issue(String name) {
        this.name = name;
        this.status = NEW;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public List<IssueComment> getComments() {
        return unmodifiableList(comments);
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void changeStatusTo(IssueStatus newStatus) {
        if (this.status == DONE && newStatus == NEW || this.status == NEW && newStatus == DONE) {
            throw new RuntimeException(String.format("Cannot change issue status from %s to %s", this.status, newStatus));
        }
        this.status = newStatus;
    }

    public void addComment(String comment) {
        if (status == DONE) {
            throw new RuntimeException("Cannot add comment to done issue");
        }
        comments.add(new IssueComment(comment));
    }
}

