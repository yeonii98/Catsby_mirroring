package com.hanium.catsby.Town.repository;

import com.hanium.catsby.Town.domain.TownComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownCommentRepository extends JpaRepository<TownComment, Integer> {
}
