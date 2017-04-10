package pl.pragmatists.ddd.adm.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class IssueId implements Serializable {

    private Long issueId;

    private IssueId() { }

    private IssueId(Long issueId) {
        this.issueId = issueId;
    }

    public static IssueId from(String issueId) {
        return new IssueId(Long.valueOf(issueId));
    }

    public Long value() {
        return issueId;
    }

    public IssueId next() {
        return new IssueId(++issueId);
    }
}
