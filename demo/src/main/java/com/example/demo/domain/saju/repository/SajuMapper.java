package com.example.demo.domain.saju.repository;

import com.example.demo.domain.saju.entity.Saju;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface SajuMapper {

    int upsert(Saju saju);

    Optional<Saju> findByUserId(@Param("userId") Long userId);
}
