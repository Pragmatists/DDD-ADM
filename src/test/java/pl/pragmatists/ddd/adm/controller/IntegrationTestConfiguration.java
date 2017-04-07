package pl.pragmatists.ddd.adm.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.pragmatists.ddd.adm.repository.IssueRepository;
import pl.pragmatists.ddd.adm.service.IssueService;

@Configuration
@EnableAutoConfiguration
public class IntegrationTestConfiguration {

    @Bean
    public IssueController issueController(IssueService issueService) throws Exception {
        return new IssueController(issueService);
    }

    @Bean
    public IssueService issueService(IssueRepository issueRepository) throws Exception {
        return new IssueService(issueRepository);
    }

    @Bean
    public TestRestTemplate restTemplate(TestRestTemplate testRestTemplate) throws Exception {
        return testRestTemplate;
    }
}
