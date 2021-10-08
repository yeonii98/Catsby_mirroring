package com.hanium.catsby.domain.town.repository;

import com.hanium.catsby.domain.town.model.TownCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownCommunityRepository extends JpaRepository<TownCommunity, Integer> {
    List<TownCommunity> findByUser_AddressOrderById(String address);
}
