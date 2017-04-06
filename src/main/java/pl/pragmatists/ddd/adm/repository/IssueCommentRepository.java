package pl.pragmatists.ddd.adm.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pragmatists.ddd.adm.model.IssueComment;

import java.util.List;

public interface IssueCommentRepository extends CrudRepository<IssueComment, Long> {
    List<IssueComment> findByIssue_Id(Long issue);
}
