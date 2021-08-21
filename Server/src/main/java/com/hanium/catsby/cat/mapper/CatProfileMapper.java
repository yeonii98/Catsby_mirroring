package com.hanium.catsby.cat.mapper;

import com.hanium.catsby.cat.model.CatProfile;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.sql.Blob;
import java.text.SimpleDateFormat;
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
    @Insert("INSERT INTO Cat(name, health, address, gender, image, content, spayed) VALUES (#{name}, #{health}, #{address}, #{gender}, #{image}, #{content}, #{spayed})")
    //@Options(useGeneratedKeys = true, keyProperty = "cat_id")
    int insertCatProfile(@Param("name") String name,
                         @Param("health") String health, @Param("address") String address,
                         @Param("gender") Boolean gender, @Param("image") Blob image,
                         @Param("content") String content, @Param("spayed") Boolean spayed);

    //고양이 수정
    @Update("UPDATE Cat SET cat_id=#{cat_id}, name=#{name}, health=#{health}, address=#{address}, gender=#{gender}, image=#{image}, content=#{content}, spayed=#{spayed} WHERE cat_id=#{cat_id}")
    int updateCatProfile(
            @Param("cat_id") int cat_id, @Param("name") String name,
            @Param("health") String health, @Param("address") String address,
            @Param("gender") Boolean gender, @Param("image") Blob image,
            @Param("content") String content, @Param("spayed") Boolean spayed);

    //고양이 삭제
    @Delete("DELETE FROM Cat Where cat_id=#{cat_id}")
    int deleteCatProfile(@Param("cat_id") int cat_id);
}
