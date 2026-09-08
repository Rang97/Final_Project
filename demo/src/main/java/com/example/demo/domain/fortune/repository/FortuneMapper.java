package com.example.demo.domain.fortune.repository;

import org.apache.ibatis.annotations.*;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface FortuneMapper {
    record Stored(String responseJson) {}
    record GameRow(Long gameId, String gameName, String tag) {}

    @Select("SELECT response_json FROM daily_fortune WHERE user_id=#{userId} AND fortune_date=#{date}")
    Stored find(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Select("""
        SELECT g.game_id AS gameId, g.name_ko AS gameName, t.name AS tag
        FROM user_game ug JOIN game g ON g.game_id=ug.game_id
        LEFT JOIN game_tag_map m ON m.game_id=g.game_id
        LEFT JOIN game_tag t ON t.tag_id=m.tag_id
        WHERE ug.user_id=#{userId}
        ORDER BY ug.is_main DESC, ug.user_game_id, t.name
        """)
    List<GameRow> games(Long userId);

    @Insert("""
        INSERT INTO fortune_generation(user_id, fortune_date, status)
        VALUES(#{userId}, #{date}, 'PROCESSING')
        """)
    int claim(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Select("SELECT status FROM fortune_generation WHERE user_id=#{userId} AND fortune_date=#{date}")
    String status(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Update("""
        UPDATE fortune_generation SET status=#{status}, updated_at=CURRENT_TIMESTAMP
        WHERE user_id=#{userId} AND fortune_date=#{date} AND status='PROCESSING'
        """)
    int finish(@Param("userId") Long userId, @Param("date") LocalDate date, @Param("status") String status);

    @Insert("""
        INSERT INTO daily_fortune(user_id, fortune_date, response_json)
        VALUES(#{userId}, #{date}, #{json})
        """)
    int save(@Param("userId") Long userId, @Param("date") LocalDate date, @Param("json") String json);
}

