package com.clone.fccreddit.repository;

import com.clone.fccreddit.model.Post;
import com.clone.fccreddit.model.User;
import com.clone.fccreddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
