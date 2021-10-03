package com.hanium.catsby.domain.town.repository;

import com.hanium.catsby.domain.town.model.TownComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownCommentRepository extends JpaRepository<TownComment, Integer> {
    List<TownComment> findByTownCommunity_Id(int id);
}
