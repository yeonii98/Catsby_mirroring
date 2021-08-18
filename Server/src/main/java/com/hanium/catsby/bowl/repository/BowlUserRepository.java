package com.hanium.catsby.bowl.repository;

import com.hanium.catsby.bowl.domain.BowlUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BowlUserRepository extends JpaRepository<BowlUser, Long> {
}
