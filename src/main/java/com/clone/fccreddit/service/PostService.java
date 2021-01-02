package com.clone.fccreddit.service;

import com.clone.fccreddit.dto.PostRequest;
import com.clone.fccreddit.dto.PostResponse;
import com.clone.fccreddit.exceptions.PostNotFoundException;
import com.clone.fccreddit.exceptions.SubredditNotFoundException;
import com.clone.fccreddit.exceptions.UsernameNotFoundException;
import com.clone.fccreddit.mapper.PostMapper;
import com.clone.fccreddit.model.Post;
import com.clone.fccreddit.model.SubReddit;
import com.clone.fccreddit.model.User;
import com.clone.fccreddit.repository.PostRepository;
import com.clone.fccreddit.repository.SubRedditRepository;
import com.clone.fccreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final SubRedditRepository subRedditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final SubRedditRepository subredditRepository;
    private final UserRepository userRepository;


    public void save(PostRequest postRequest) {
        SubReddit subreddit = subRedditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        User current_user = authService.getCurrentUser();

        Post post = postMapper.map(postRequest, subreddit, current_user);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        SubReddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        List<Post> posts =  postRepository.findByUser(user);
                return posts
                        .stream()
                        .map(postMapper::mapToDto)
                        .collect(toList());
    }
}
