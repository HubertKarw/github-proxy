package com.hubertkarw.github_proxy.repository;

import com.hubertkarw.github_proxy.model.GitRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitRepositoryJpaRepository extends JpaRepository<GitRepository, Long> {
}
