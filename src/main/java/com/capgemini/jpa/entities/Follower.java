package com.capgemini.jpa.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedEntityGraph(name = "Follower.comment",
attributeNodes = @NamedAttributeNode(value = "comment", subgraph = "subgraph.event"),
subgraphs = @NamedSubgraph(name = "subgraph.event", attributeNodes = @NamedAttributeNode("event")))
public class Follower {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    private String userId;
}
