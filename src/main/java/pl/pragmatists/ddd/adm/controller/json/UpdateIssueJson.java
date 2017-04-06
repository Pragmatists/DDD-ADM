package pl.pragmatists.ddd.adm.controller.json;

import pl.pragmatists.ddd.adm.model.IssueStatus;

public class UpdateIssueJson {

    public IssueStatus status;

    public UpdateIssueJson() { }

    public UpdateIssueJson(IssueStatus status) {
        this.status = status;
    }
}
