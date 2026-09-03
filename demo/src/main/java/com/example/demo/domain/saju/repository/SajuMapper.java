package com.example.demo.domain.saju.repository;

import com.example.demo.domain.saju.dto.SajuElementDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SajuMapper {

    // 파티원 사주 전체 조회
    List<SajuElementDto> findElementsByUserId(@Param("userIds") List<Long> userIds);

}
