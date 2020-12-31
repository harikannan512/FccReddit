package com.clone.fccreddit.service;

import com.clone.fccreddit.dto.SubredditDto;
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

//    POST REQUEST
    private SubReddit subredditdDtoBuilder(SubredditDto subredditDto) {
        return SubReddit.builder()
                .name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();
    }

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        SubReddit save = subRedditRepository.save(subredditdDtoBuilder(subredditDto));
        subredditDto.setId(save.getId());

        return subredditDto;
    }
//  GET REQUEST
    private SubredditDto mapToDto(SubReddit subReddit) {
        return SubredditDto.builder().name(subReddit.getName())
                .description(subReddit.getDescription())
                .id((subReddit.getId()))
                .build();
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subRedditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }
}
