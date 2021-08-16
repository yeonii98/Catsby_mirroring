package com.hanium.catsby.town.repository;

import com.hanium.catsby.town.domain.TownCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownCommunityRepository extends JpaRepository<TownCommunity, Integer> {
    List<TownCommunity> findAllByOrderByIdDesc();
}
