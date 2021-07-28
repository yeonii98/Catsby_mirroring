package com.hanium.catsby.TownCommunity.repository;

import com.hanium.catsby.domain.TownCommunity;
import com.hanium.catsby.domain.TownLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownCommunityRepository extends JpaRepository<TownCommunity, Integer> {
}
