package com.hanium.catsby.Town.repository;

import com.hanium.catsby.Town.domain.TownLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownLikeRepository extends JpaRepository<TownLike, Integer> {
    void deleteByTownCommunity_IdAndUser_Id(int townCommunity_id, long user_id);
    int countByTownCommunity_IdAndUser_Id(int townCommunity_id, long user_id);
}
