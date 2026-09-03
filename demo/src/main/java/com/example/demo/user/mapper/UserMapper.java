package com.example.demo.user.mapper;

import com.example.demo.user.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT EXISTS(SELECT 1 FROM `user` WHERE login_id = #{loginId})")
    boolean existsByLoginId(@Param("loginId") String loginId);

    @Insert("""
            INSERT INTO `user` (login_id, password, nickname, role)
            VALUES (#{loginId}, #{password}, #{nickname}, #{role})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
}
