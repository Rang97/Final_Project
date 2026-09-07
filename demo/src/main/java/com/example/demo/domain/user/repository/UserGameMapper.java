package com.example.demo.domain.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserGameMapper {

    boolean existsGame(@Param("gameId") Long gameId);

    boolean existsByUserIdAndGameId(@Param("userId") Long userId, @Param("gameId") Long gameId);

    int countByUserId(@Param("userId") Long userId);

    int insert(@Param("userId") Long userId, @Param("gameId") Long gameId);

    int clearMainByUserId(@Param("userId") Long userId);

    int setMain(@Param("userId") Long userId, @Param("gameId") Long gameId);

    int deleteByUserIdAndGameId(@Param("userId") Long userId, @Param("gameId") Long gameId);
}
