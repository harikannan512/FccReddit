package com.clone.fccreddit.service;

import com.clone.fccreddit.dto.VoteDto;
import com.clone.fccreddit.exceptions.PostNotFoundException;
import com.clone.fccreddit.exceptions.SpringRedditException;
import com.clone.fccreddit.model.Post;
import com.clone.fccreddit.model.Vote;
import com.clone.fccreddit.repository.PostRepository;
import com.clone.fccreddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.clone.fccreddit.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {

    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;

    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID " + voteDto.getPostId().toString()));
        Optional<Vote> voteByPostAndUser = voteRepository
                .findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get()
                .getVoteType().
                        equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already " + voteDto.getVoteType() +"'d for this post");
        }

        if (UPVOTE.equals(voteDto.getVoteType())) {
            System.out.println(post.getVoteCount().toString());
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
