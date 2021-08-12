package com.hanium.catsby.notification.repository;

import com.hanium.catsby.notification.domain.Notification;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
