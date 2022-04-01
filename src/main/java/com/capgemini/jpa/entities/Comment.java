package com.capgemini.jpa.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Follower> followers;

    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    private String content;
}
