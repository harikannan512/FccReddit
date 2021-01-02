package com.clone.fccreddit.mapper;

import com.clone.fccreddit.dto.SubredditDto;
import com.clone.fccreddit.model.Post;
import com.clone.fccreddit.model.SubReddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target="numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    @Mapping(target = "Id", expression = "java(subreddit.getId())")
    SubredditDto mapSubredditToDto(SubReddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts){
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "Id", expression = "java(subredditDto.getId())")
    SubReddit mapDtoToSubreddit(SubredditDto subredditDto);
}
