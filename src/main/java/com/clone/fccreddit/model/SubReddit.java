package com.clone.fccreddit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubReddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Community needs a name")
    private String name;
    @NotBlank(message = "Community needs a description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
