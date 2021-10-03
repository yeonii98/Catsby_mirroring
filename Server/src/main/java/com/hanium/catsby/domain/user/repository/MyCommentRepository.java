package com.hanium.catsby.domain.user.repository;

import com.hanium.catsby.domain.user.model.MyComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyCommentRepository extends JpaRepository<MyComment,Integer> {
    List<MyComment> findByTownComment_User_UidOrBowlComment_User_UidOrderByMyCommentIdDesc(String t_user_uid, String b_user_uid);

    void deleteByTownComment_Id(int townComment_id);
    MyComment findByTownComment_Id(int townComment_id);

    void deleteByBowlComment_Id(long bowlComment_id);
    MyComment findByBowlComment_Id(long townComment_id);

}
