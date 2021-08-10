package com.hanium.catsby.user.repository;

import com.hanium.catsby.user.domain.MyComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyCommentRepository extends JpaRepository<MyComment,Integer> {
    List<MyComment> findByTownComment_User_IdOrBowlComment_User_IdOrderByMyCommentIdDesc(long t_user_id, long b_user_id);

    void deleteByTownComment_Id(int townComment_id);
    MyComment findByTownComment_Id(int townComment_id);

    void deleteByBowlComment_Id(long bowlComment_id);
    MyComment findByBowlComment_Id(long townComment_id);

}
