package com.clone.fccreddit.mapper;

import com.clone.fccreddit.dto.SubredditDto;
import com.clone.fccreddit.model.Post;
import com.clone.fccreddit.model.SubReddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target="numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(SubReddit subReddit);

    default Integer mapPosts(List<Post> numberOfPosts){
        return numberOfPosts.size();
    }
}