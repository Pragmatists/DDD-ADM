package pl.pragmatists.ddd.adm.application;

import pl.pragmatists.ddd.adm.domain.IssueStatus;

public class UpdateIssueJson {

    public IssueStatus status;

    public UpdateIssueJson() { }

    public UpdateIssueJson(IssueStatus status) {
        this.status = status;
    }
}
