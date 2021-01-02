package com.clone.fccreddit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data // getters and setters at compile time
@Entity
@AllArgsConstructor // constructor at compile time
@NoArgsConstructor // constructor at compile time
@Builder // generate builder methods
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotBlank(message = "Post Name cannot be Null or empty")
    private String postName;
    @Nullable
    private String url;

    @Lob
    @Nullable
    private String description;

    private Integer voteCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    private Instant createdDate;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private SubReddit subreddit;



}
