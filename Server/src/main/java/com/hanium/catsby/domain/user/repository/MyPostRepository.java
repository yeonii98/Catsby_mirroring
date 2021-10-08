package com.hanium.catsby.domain.user.repository;

import com.hanium.catsby.domain.user.model.MyPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPostRepository extends JpaRepository<MyPost,Integer> {
    List<MyPost> findByTownCommunity_User_UidOrBowlCommunity_User_UidOrderByMyPostIdDesc(String t_user_uid, String c_user_uid);

    void deleteByTownCommunity_Id(int townCommunity_id);
    MyPost findByTownCommunity_Id(int townCommunity_id);

    void deleteByBowlCommunity_Id(long bowlCommunity_id);
    MyPost findByBowlCommunity_Id(long townCommunity_id);
}
