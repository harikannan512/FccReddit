package com.clone.fccreddit.repository;

import com.clone.fccreddit.model.Post;
import com.clone.fccreddit.model.SubReddit;
import com.clone.fccreddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(SubReddit subreddit);

    List<Post> findByUser(User user);
}
