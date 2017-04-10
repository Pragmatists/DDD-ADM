package pl.pragmatists.ddd.adm.infrastructure;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.pragmatists.ddd.adm.domain.Issue;
import pl.pragmatists.ddd.adm.domain.IssueId;

public interface CrudIssueRepository extends CrudRepository<Issue, IssueId> {
    @Query("select max(t.id) from Issue t")
    IssueId findTopById();
}
