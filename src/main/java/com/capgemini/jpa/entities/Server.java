package com.capgemini.jpa.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update server set is_active = false where id=?")
@Where(clause = "is_active=true")
public class Server {

    @Id
    @SequenceGenerator(name = "SERVER_ID_GENERATOR", sequenceName = "SERVER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVER_ID_GENERATOR")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ip;

    @Version
    private Long version;

    private LocalDateTime createdDate;
    private LocalDateTime lastUpdateDate;

    @Column(nullable = false, name="is_active", columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @PreRemove
    protected void onDelete() {
        isActive = false;
    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdateDate = LocalDateTime.now();
    }

    public Server(String name, String ip) {
        super();
        this.name = name;
        this.ip = ip;
    }
}
