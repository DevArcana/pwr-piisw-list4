package com.capgemini.jpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Event {

    @Id
    @SequenceGenerator(name = "EVENT_ID_GENERATOR", sequenceName = "EVENT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_ID_GENERATOR")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime time;

    private int duration;

    @Column(length = 10)
    private String threadId;

    @Column(length = 30)
    private String userId;

    @Column(length = 100)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SERVER_ID", nullable = false)
    private Server server;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Column
    private boolean analysisRequired;
}
