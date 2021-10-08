package com.hanium.catsby.town.repository;

import com.hanium.catsby.town.domain.TownLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownLikeRepository extends JpaRepository<TownLike, Integer> {
    void deleteByTownCommunity_IdAndUser_Uid(int townCommunity_id, String uid);
    int countByTownCommunity_IdAndUser_Uid(int townCommunity_id, String uid);
}
