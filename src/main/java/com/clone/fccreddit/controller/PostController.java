package com.clone.fccreddit.controller;

import com.clone.fccreddit.dto.PostRequest;
import com.clone.fccreddit.dto.PostResponse;
import com.clone.fccreddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long id){
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(String name){
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }
}
