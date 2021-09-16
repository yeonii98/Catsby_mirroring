package com.hanium.catsby.bowl.repository;

import com.hanium.catsby.bowl.domain.BowlFeed;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BowlFeedRepository extends JpaRepository<BowlFeed, Long> {

    List<BowlFeed> findByBowlId(Long bowlId, Sort sort);
}
