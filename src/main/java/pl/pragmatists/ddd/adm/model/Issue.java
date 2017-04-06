package pl.pragmatists.ddd.adm.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy= AUTO)
    private Long id;
    private String name;
    private IssueStatus status;
    @Transient
    private List<IssueComment> comments = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public List<IssueComment> getComments() {
        return comments;
    }

    public void setComments(List<IssueComment> comments) {
        this.comments = comments;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public IssueStatus getStatus() {
        return status;
    }
}
