package com.hanium.catsby.Town.repository;

import com.hanium.catsby.Town.domain.TownComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownCommentRepository extends JpaRepository<TownComment, Integer> {
    List<TownComment> findByTownCommunity_Id(int id);
}
