package com.clone.fccreddit.repository;

import com.clone.fccreddit.model.Comment;
import com.clone.fccreddit.model.Post;
import com.clone.fccreddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
