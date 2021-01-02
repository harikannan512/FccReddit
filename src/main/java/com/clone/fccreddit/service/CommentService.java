package com.clone.fccreddit.service;

import com.clone.fccreddit.dto.CommentsDto;
import com.clone.fccreddit.exceptions.PostNotFoundException;
import com.clone.fccreddit.exceptions.UsernameNotFoundException;
import com.clone.fccreddit.mapper.CommentMapper;
import com.clone.fccreddit.model.Comment;
import com.clone.fccreddit.model.NotificationEmail;
import com.clone.fccreddit.model.Post;
import com.clone.fccreddit.model.User;
import com.clone.fccreddit.repository.CommentRepository;
import com.clone.fccreddit.repository.PostRepository;
import com.clone.fccreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {

    private static final String POST_URL = "";

    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final MailService mailService;

    public void save(CommentsDto commentsDto){
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        User user = authService.getCurrentUser();

        Comment comment = commentMapper.map(commentsDto, post, user);
        commentRepository.save(comment);

        String message = post.getUser().getUsername() + " posted a comment on your post. \n" + POST_URL;
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper :: mapToDto)
                .collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUsername(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}
