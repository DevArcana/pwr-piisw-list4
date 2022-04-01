package com.capgemini.jpa.tasks;

import com.capgemini.jpa.entities.Comment;
import com.capgemini.jpa.repositories.EventRepository;
import com.capgemini.jpa.repositories.FollowerRepository;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.transaction.TestTransaction;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@DataJpaTest
class Task6 {

    @Autowired
    private FollowerRepository repository;

    @Test
    void shouldReturnFollowersByUserId() {
        assertThat(repository.findAllByUserId("adam"), hasSize(7));
        assertThat(repository.findAllByUserId("bartek"), hasSize(1));
    }

    @Test
    void shouldContainRelevantFields() {
        var follower = repository.findAllByUserId("bartek").get(0);
        TestTransaction.end();
        var comment = follower.getComment();
        var event = comment.getEvent();
        assertThat(follower.getUserId(), notNullValue());
        assertThat(comment.getContent(), notNullValue());
        assertThat(event.getDescription(), notNullValue());
        assertThat(event.getTime(), notNullValue());
        assertThrows(LazyInitializationException.class, () -> event.getServer().getName());
    }
}
