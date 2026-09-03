package com.example.demo.domain.user.repository;

import com.example.demo.domain.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {

    boolean existsByLoginId(@Param("loginId") String loginId);

    int insert(User user);

    Optional<User> findByLoginId(@Param("loginId") String loginId);

    Optional<User> findById(@Param("userId") Long userId);
}
