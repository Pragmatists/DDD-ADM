package pl.pragmatists.ddd.adm.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class IssueComment {

    @Id
    @GeneratedValue(strategy= AUTO)
    private Long id;

    private String comment;

    @OneToOne
    private Issue issue;

    public IssueComment() { }

    public IssueComment(String comment, Issue issue) {
        this.comment = comment;
        this.issue = issue;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }
}
