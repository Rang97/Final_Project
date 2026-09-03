package com.example.demo.domain.saju.repository;

import com.example.demo.domain.saju.entity.SajuInput;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SajuInputMapper {

    int insert(SajuInput sajuInput);
}
