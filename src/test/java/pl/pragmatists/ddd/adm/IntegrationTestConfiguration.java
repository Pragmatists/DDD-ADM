package pl.pragmatists.ddd.adm;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.pragmatists.ddd.adm.application.IssueController;
import pl.pragmatists.ddd.adm.domain.IssueIdSequenceGenerator;
import pl.pragmatists.ddd.adm.domain.IssueRepository;
import pl.pragmatists.ddd.adm.application.IssueService;

@Configuration
@EnableAutoConfiguration
public class IntegrationTestConfiguration {

    @Bean
    public IssueController issueController(IssueService issueService) throws Exception {
        return new IssueController(issueService);
    }

    @Bean
    public IssueService issueService(IssueRepository issueRepository, IssueIdSequenceGenerator issueIdSequenceGenerator) throws Exception {
        return new IssueService(issueRepository, issueIdSequenceGenerator);
    }

    @Bean
    public TestRestTemplate restTemplate(TestRestTemplate testRestTemplate) throws Exception {
        return testRestTemplate;
    }
}
