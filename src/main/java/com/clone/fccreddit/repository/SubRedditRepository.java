package com.clone.fccreddit.repository;

import com.clone.fccreddit.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubRedditRepository extends JpaRepository<SubReddit, Long> {
}
