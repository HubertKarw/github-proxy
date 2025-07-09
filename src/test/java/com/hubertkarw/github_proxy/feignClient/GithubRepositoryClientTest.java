package com.hubertkarw.github_proxy.feignClient;

import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.hubertkarw.github_proxy.model.GitRepository;
import com.hubertkarw.github_proxy.model.GitRepositoryInfo;
import feign.RetryableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;


import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(
        port = 8081
)
public class GithubRepositoryClientTest {
    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private GithubRepositoryClient client;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        wireMockServer.start();
    }

    @BeforeEach
    void shutdown() {
        wireMockServer.resetAll();
        wireMockServer.stop();
    }

    @Test
    void getRepository_validNameAndOwner_returnRepository() throws JsonProcessingException {
        GitRepositoryInfo info = GitRepositoryInfo.builder()
                .fullName("HubertKarw/github-proxy")
                .description(null)
                .createdAt(LocalDateTime.of(2022, 12, 12, 12, 12))
                .build();

        wireMockServer.stubFor(get("/repos/HubertKarw/github-proxy")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mapper.writeValueAsString(info))));

        var response = client.getGitRepository("HubertKarw", "github-proxy");

        assertAll(
                () -> assertEquals("HubertKarw/github-proxy", response.getFullName())
        );
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assertions.assertEquals(("[{ \"fullName\": \"HubertKarw/github-proxy\",\"description\": null,\"cloneUrl\": \"https://github.com/HubertKarw/github-proxy.git\",\"stars\": 1, \"createdAt\": \"2025-05-30T15:31:57\"}]"), response.getBody());
    }

    @Test
    void getUnknown_notKnownPath_returnNotFound() {
        stubFor(get("/repoes").willReturn(aResponse().withStatus(404)));
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8081/repoes", String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    //    @Test
//    void givenWireMockStub_whenGetPing_thenReturnsPong() {
//        stubFor(WireMock.get("/ping").willReturn(ok("pong")));
//
//        ResponseEntity<String> response = restTemplate.getForEntity(wireMockUrl + "/ping", String.class);
//
//        Assertions.assertEquals("pong", response.getBody());
//    }
    @Test
    void getDetails_serviceError500_shouldThrowException(){
        String owner = "HubertKarw";
        String repositoryName = "example-repo";


        wireMockServer.stubFor(WireMock.get("/repos/HubertKarw/example-repo")
                .willReturn(aResponse()
                        .withStatus(500)
                ));
        Assertions.assertThrows(RetryableException.class, () -> client.getGitRepository(owner, repositoryName));

        verify(3, getRequestedFor(urlEqualTo("/repos/HubertKarw/example-repo")));

    }
    @Test
    void getDetails_serviceError503_shouldThrowException(){
        String owner = "HubertKarw";
        String repositoryName = "example-repo";


        wireMockServer.stubFor(WireMock.get("/repos/HubertKarw/example-repo")
                .willReturn(aResponse()
                        .withStatus(503)
                ));
        Assertions.assertThrows(RetryableException.class, () -> client.getGitRepository(owner, repositoryName));

        verify(3, getRequestedFor(urlEqualTo("/repos/HubertKarw/example-repo")));

    }
}
