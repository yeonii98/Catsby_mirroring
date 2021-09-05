package com.hanium.catsby.bowl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanium.catsby.user.domain.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Bowl_Feed")
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class BowlFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bowlFeed_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowl_id")
    @JsonIgnore
    private Bowl bowl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdDate;
}
