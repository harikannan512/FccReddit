package com.clone.fccreddit.mapper;


import com.clone.fccreddit.dto.PostRequest;
import com.clone.fccreddit.dto.PostResponse;
import com.clone.fccreddit.model.Post;
import com.clone.fccreddit.model.SubReddit;
import com.clone.fccreddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    Post map(PostRequest postRequest, SubReddit subreddit, User user);

    @Mapping(target = "id", source="postId")
    @Mapping(target ="postName", source="postName")
    @Mapping(target ="description", source="description")
    @Mapping(target ="url", source="url")
    @Mapping(target ="subredditName", source="subreddit.name")
    @Mapping(target ="userName", source="user.username")
    PostResponse mapToDto(Post post);

}
