package com.hanium.catsby.TownComment.repository;

import com.hanium.catsby.domain.TownComment;
import com.hanium.catsby.domain.TownLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownCommentRepository extends JpaRepository<TownComment, Integer> {
}
