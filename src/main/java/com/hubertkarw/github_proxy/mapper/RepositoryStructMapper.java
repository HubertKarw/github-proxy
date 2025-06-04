package com.hubertkarw.github_proxy.mapper;

import com.hubertkarw.github_proxy.model.CreateGitRepositoryCommand;
import com.hubertkarw.github_proxy.model.GitRepository;
import com.hubertkarw.github_proxy.model.GitRepositoryDTO;
import com.hubertkarw.github_proxy.model.GitRepositoryInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepositoryStructMapper {
    GitRepository mapToRepository (CreateGitRepositoryCommand command);
    GitRepositoryDTO mapToDTO (GitRepository repository);
    GitRepository toEntity (GitRepositoryInfo repositoryInfo);
}
