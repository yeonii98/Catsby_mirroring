package com.hanium.catsby.domain.notification.repository;

import com.hanium.catsby.domain.notification.model.Notification;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
