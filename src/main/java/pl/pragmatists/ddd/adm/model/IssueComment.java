package pl.pragmatists.ddd.adm.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class IssueComment {

    @Id
    @GeneratedValue(strategy= AUTO)
    private Long id;

    private String comment;

    private IssueComment() { }

    public IssueComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }
}
