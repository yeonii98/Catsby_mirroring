package com.hanium.catsby.MyWriting.repository;

import com.hanium.catsby.BowlCommunity.domain.BowlCommunity;
import com.hanium.catsby.MyWriting.domain.MyPost;
import com.hanium.catsby.Town.domain.TownCommunity;
import com.hanium.catsby.User.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPostRepository extends JpaRepository<MyPost,Integer> {
    List<MyPost> findByTownCommunity_User_IdOrBowlCommunity_User_IdOrderByMyPostIdDesc(long t_user_id, long c_user_id);


    void deleteByTownCommunity_Id(int townCommunity_id);
    MyPost findByTownCommunity_Id(int townCommunity_id);

    void deleteByBowlCommunity_Id(long bowlCommunity_id);
    MyPost findByBowlCommunity_Id(long townCommunity_id);
}
