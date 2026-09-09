package com.example.demo.domain.saju.repository;

import com.example.demo.domain.saju.entity.SajuInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface SajuInputMapper {

    int insert(SajuInput sajuInput);

    int updateByUserId(SajuInput sajuInput);

    Optional<SajuInput> findByUserId(@Param("userId") Long userId);
}
