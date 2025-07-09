package com.hubertkarw.github_proxy.service;

import com.hubertkarw.github_proxy.exception.GitRepositoryException;
import com.hubertkarw.github_proxy.feignClient.GithubRepositoryClient;
import com.hubertkarw.github_proxy.mapper.RepositoryStructMapper;
import com.hubertkarw.github_proxy.model.GitRepository;
import com.hubertkarw.github_proxy.model.GitRepositoryDTO;
import com.hubertkarw.github_proxy.model.GitRepositoryInfo;
import com.hubertkarw.github_proxy.repository.GitRepositoryJpaRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GitRepositoryService {
    private final GitRepositoryJpaRepository jpaRepository;
    private final GithubRepositoryClient client;
    private final RepositoryStructMapper mapper;

    public GitRepositoryDTO getRepositoryInfo(String owner, String repository) {
        try {
            return mapper.mapToDTO(mapper.toEntity(client.getGitRepository(owner, repository)));
        } catch (FeignException.FeignClientException.NotFound ex) {
            throw new GitRepositoryException("Requested repo could not be found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public GitRepositoryDTO saveRepo(String owner, String repository) {
        try {
            GitRepositoryInfo info = client.getGitRepository(owner, repository);
            return mapper.mapToDTO(jpaRepository.save(mapper.toEntity(info)));
        } catch (FeignException.FeignClientException.NotFound ex) {
            throw new GitRepositoryException("Requested repo could not be found", HttpStatus.NOT_FOUND);
        }

    }

    public GitRepositoryDTO getLocalRepo(String owner, String repository) {
        return mapper.mapToDTO(jpaRepository.findByFullName(GitRepository.generateFullName(owner, repository))
                .orElseThrow(() -> new GitRepositoryException("Cannot find local repo", HttpStatus.NOT_FOUND)));
    }

    @Transactional
    public GitRepositoryDTO updateRepo(String owner, String repository) {
        GitRepository repoToUpdate = jpaRepository.findByFullName(GitRepository.generateFullName(owner, repository))
                .orElseThrow(() -> new GitRepositoryException("Cannot find local repo", HttpStatus.NOT_FOUND));
        try {
            repoToUpdate.update(mapper.toEntity(client.getGitRepository(owner, repository)));
        } catch (FeignException.FeignClientException.NotFound ex) {
            throw new GitRepositoryException("Requested repo could not be found", HttpStatus.NOT_FOUND);
        }
        return mapper.mapToDTO(jpaRepository.save(repoToUpdate));
    }

    @Transactional
    public void deleteRepo(String owner, String repository) {
        GitRepository repo = jpaRepository.findByFullName(GitRepository.generateFullName(owner, repository))
                .orElseThrow(() -> new GitRepositoryException("Cannot find local repo", HttpStatus.NOT_FOUND));
        jpaRepository.delete(repo);
    }
}
