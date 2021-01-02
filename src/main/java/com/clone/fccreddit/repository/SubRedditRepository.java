package com.clone.fccreddit.repository;

import com.clone.fccreddit.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubRedditRepository extends JpaRepository<SubReddit, Long> {
    Optional<SubReddit> findByName(String subredditName);
}
