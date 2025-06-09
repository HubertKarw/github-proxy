package com.hubertkarw.github_proxy.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.hubertkarw.github_proxy.model.GitRepository;
import com.hubertkarw.github_proxy.model.GitRepositoryInfo;
import com.hubertkarw.github_proxy.repository.GitRepositoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8081)
public class IntegrationTests {

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GitRepositoryJpaRepository repository;

    @LocalServerPort
    int appPort;

    @Test
    void getDetails_dataCorrect_shouldReturnRepositoryInfo() throws JsonProcessingException {
        String owner = "HubertKarw";
        String repositoryName = "example-repo";

        GitRepositoryInfo gitRepositoryInfo = GitRepositoryInfo.builder()
                .fullName(GitRepository.generateFullName(owner, repositoryName))
                .build();

        wireMockServer.stubFor(WireMock.get("/repos/HubertKarw/example-repo")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(gitRepositoryInfo))
                ));

        String url = String.format("http://localhost:%s/repositories/%s/%s", appPort, owner, repositoryName);
        ResponseEntity<GitRepository> response = restTemplate.getForEntity(url, GitRepository.class);

        assertAll(
                () -> assertEquals("HubertKarw/example-repo", response.getBody().getFullName())
        );
    }

    @Test
    void getLocalData_dataCorrect_shouldReturnLocalRepositoryInfo() throws JsonProcessingException {
        String owner = "HubertKarw";
        String repositoryName = "example-repo";

        GitRepository gitRepository = GitRepository.builder()
                .fullName(GitRepository.generateFullName(owner, repositoryName))
                .cloneUrl("url")
                .createdAt(LocalDateTime.now())
                .stars(1L)
                .description("desc")
                .build();

        repository.save(gitRepository);


//        wireMockServer.stubFor(WireMock.get("/local/repositories/HubertKarw/example-repo")
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withBody(objectMapper.writeValueAsString(gitRepository))
//                ));

        String url = String.format("http://localhost:%s/local/repositories/%s/%s", appPort, owner, repositoryName);
        ResponseEntity<GitRepository> response = restTemplate.getForEntity(url, GitRepository.class);

        assertAll(
                () -> assertEquals("HubertKarw/example-repo", response.getBody().getFullName())
        );
    }

    @Test
    void postToLocal_dataCorrect_shouldReturnRepositoryInfo() throws JsonProcessingException {
        String owner = "HubertKarw";
        String repositoryName = "example-repo";

        GitRepositoryInfo gitRepositoryInfo = GitRepositoryInfo.builder()
                .fullName(GitRepository.generateFullName(owner, repositoryName))
                .cloneUrl("url")
                .createdAt(LocalDateTime.now())
                .stars(1L)
                .description("desc")
                .build();

        GitRepository gitRepository = GitRepository.builder()
                .fullName(GitRepository.generateFullName(owner, repositoryName))
                .cloneUrl("url")
                .createdAt(LocalDateTime.now())
                .stars(1L)
                .description("desc")
                .build();

        wireMockServer.stubFor(WireMock.get("/repos/HubertKarw/example-repo")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(gitRepositoryInfo))
                ));

        String url = String.format("http://localhost:%s/repositories/%s/%s", appPort, owner, repositoryName);
        ResponseEntity<GitRepository> response = restTemplate.postForEntity(url, gitRepository, GitRepository.class);

        assertAll(
                () -> assertEquals("HubertKarw/example-repo", response.getBody().getFullName())
        );
    }

    @Test
    void putToLocal_dataCorrect_shouldUpdateRepositoryInfo() throws JsonProcessingException {
        String owner = "HubertKarw";
        String repositoryName = "example-repo";

        GitRepositoryInfo gitRepositoryInfo = GitRepositoryInfo.builder()
                .fullName(GitRepository.generateFullName(owner, repositoryName))
                .cloneUrl("url")
                .createdAt(LocalDateTime.now())
                .stars(1L)
                .description("desc")
                .build();

        GitRepository gitRepository = GitRepository.builder()
                .fullName(GitRepository.generateFullName(owner, repositoryName))
                .cloneUrl("url")
                .createdAt(LocalDateTime.now())
                .stars(1L)
                .description("desc")
                .build();

        GitRepository gitRepositoryToUpdate = GitRepository.builder()
                .fullName(GitRepository.generateFullName(owner, repositoryName))
                .cloneUrl("url11")
                .createdAt(LocalDateTime.now())
                .stars(0L)
                .description("desc1")
                .build();

        repository.save(gitRepository);

        wireMockServer.stubFor(WireMock.get("/repos/HubertKarw/example-repo")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(gitRepositoryInfo))
                ));

        String url = String.format("http://localhost:%s/repositories/%s/%s", appPort, owner, repositoryName);
//        ResponseEntity<GitRepository> response = restTemplate.exchange(url, gitRepository, GitRepository.class);
//    exchage
    }

    @Test
    void deleteFromLocal_dataCorrect_shouldDeleteFromRepository(){
        String owner = "HubertKarw";
        String repositoryName = "example-repo";

        GitRepository gitRepository = GitRepository.builder()
                .fullName(GitRepository.generateFullName(owner, repositoryName))
                .cloneUrl("url")
                .createdAt(LocalDateTime.now())
                .stars(1L)
                .description("desc")
                .build();

        repository.save(gitRepository);

        String url = String.format("http://localhost:%s/local/repositories/%s/%s", appPort, owner, repositoryName);
        restTemplate.delete(url);
        Mockito.verify(repository).delete(gitRepository);
    }


}
