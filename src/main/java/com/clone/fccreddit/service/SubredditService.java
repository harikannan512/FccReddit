package com.clone.fccreddit.service;

import com.clone.fccreddit.dto.SubredditDto;
import com.clone.fccreddit.exceptions.SpringRedditException;
import com.clone.fccreddit.mapper.SubredditMapper;
import com.clone.fccreddit.model.SubReddit;
import com.clone.fccreddit.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubRedditRepository subRedditRepository;
    private final SubredditMapper subredditMapper;

//    POST REQUEST
    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        SubReddit save = subRedditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());

        return subredditDto;
    }
//  GET REQUEST
    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subRedditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    public SubredditDto getSubreddit(Long id) {
        SubReddit subreddit = subRedditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No Subreddit found with the id"));

        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
