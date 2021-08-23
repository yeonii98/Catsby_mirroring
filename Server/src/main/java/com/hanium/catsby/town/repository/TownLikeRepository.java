package com.hanium.catsby.town.repository;

import com.hanium.catsby.town.domain.TownLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownLikeRepository extends JpaRepository<TownLike, Integer> {
    void deleteByTownCommunity_IdAndUser_Id(int townCommunity_id, long user_id);
    int countByTownCommunity_IdAndUser_Id(int townCommunity_id, long user_id);
}
