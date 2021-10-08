package com.hanium.catsby.domain.cat.mapper;


import com.hanium.catsby.domain.cat.model.CatProfile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CatProfileMapper {

    //고양이 목록
    @Select("SELECT * FROM Cat")
    List<CatProfile> getCatProfileList();

    //고양이 세부 조회
    @Select("SELECT * FROM Cat WHERE cat_id=#{cat_id}")
    CatProfile getCatProfile(@Param("cat_id") int cat_id);

    //고양이 등록
    @Insert("INSERT INTO Cat(user_id, user_add, name, health, address, gender, image, content, spayed) VALUES (#{userId}, #{user_add}, #{name}, #{health}, #{address}, #{gender}, #{image}, #{content}, #{spayed})")
    //@Options(useGeneratedKeys = true, keyProperty = "cat_id")
    int insertCatProfile(@Param ("userId") long userId, @Param("user_add") String user_add,
                         @Param("name") String name,
                         @Param("health") String health, @Param("address") String address,
                         @Param("gender") int gender, @Param("image") String image,
                         @Param("content") String content, @Param("spayed") int spayed);

    //고양이 수정
    @Update("UPDATE Cat SET cat_id=#{cat_id}, name=#{name}, health=#{health}, address=#{address}, gender=#{gender}, image=#{image}, content=#{content}, spayed=#{spayed} WHERE cat_id=#{cat_id}")
    int updateCatProfile(
            @Param("cat_id") int cat_id, @Param("name") String name,
            @Param("health") String health, @Param("address") String address,
            @Param("gender") int gender, @Param("image") String image,
            @Param("content") String content, @Param("spayed") int spayed);

    //고양이 삭제
    @Delete("DELETE FROM Cat Where cat_id=#{cat_id}")
    int deleteCatProfile(@Param("cat_id") int cat_id);
}
