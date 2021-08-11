package com.hanium.catsby.town.repository;

import com.hanium.catsby.town.domain.TownComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownCommentRepository extends JpaRepository<TownComment, Integer> {
}
