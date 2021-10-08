package com.hanium.catsby.domain.bowl.repository;

import com.hanium.catsby.domain.bowl.model.BowlFeed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BowlFeedRepository extends JpaRepository<BowlFeed, Long> {

    List<BowlFeed> findByBowlId(Long bowlId, Pageable pageable);
}
