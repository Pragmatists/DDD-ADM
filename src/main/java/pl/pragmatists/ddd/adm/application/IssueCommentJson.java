package pl.pragmatists.ddd.adm.application;

import pl.pragmatists.ddd.adm.domain.IssueComment;

public class IssueCommentJson {
    public String comment;

    public IssueCommentJson() { }

    public IssueCommentJson(String comment) {
        this.comment = comment;
    }

    public static IssueCommentJson fromModel(IssueComment comment) {
        return new IssueCommentJson(comment.getComment());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueCommentJson that = (IssueCommentJson) o;

        return comment != null ? comment.equals(that.comment) : that.comment == null;
    }

    @Override
    public int hashCode() {
        return comment != null ? comment.hashCode() : 0;
    }
}
