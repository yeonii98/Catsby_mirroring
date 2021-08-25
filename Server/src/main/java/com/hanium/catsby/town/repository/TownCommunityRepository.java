package com.hanium.catsby.Town.repository;

import com.hanium.catsby.Town.domain.TownCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownCommunityRepository extends JpaRepository<TownCommunity, Integer> {
}
