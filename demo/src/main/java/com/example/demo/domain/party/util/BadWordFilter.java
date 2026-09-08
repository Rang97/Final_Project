package com.example.demo.domain.party.util;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class BadWordFilter {

    private static final Set<String> Bad_words = Set.of(
            "씨발", "씨1발", "시발", "씨팔", "씨바", "쓰발",
            "개새끼", "개색기", "개새꺄", "개객꺄", "개객기", "새끼", "새꺄",
            "병신", "병1신", "븅신", "정신병자",
            "지랄", "지럴", "좆", "좆같은", "좆까",
            "존나", "존니", "졸라",
            "미친놈", "미친년", "미친새끼",
            "걸레", "창녀", "잡년", "썅년",
            "강간", "성폭행",
            "죽어", "뒤져", "꺼져", "닥쳐",
            "니미", "니애미", "느금마",
            "씹", "씹새끼", "씹할", "씨불", "씨부럴", "씨벌",
            "뒈져", "뒈진다", "자살해",
            "애미없다", "애비없다", "후레자식", "호로새끼",
            "개돼지", "개소리", "개지랄",
            "찌질이", "찌질하다",
            "급식충", "틀딱",
            "개년", "씨댕", "씨댕이", "개차반",
            "버러지", "인간쓰레기", "쓰레기같은",
            "뒤지고싶냐", "뒤질래", "처맞다", "맞고싶냐",
            "병신짓", "좆밥", "병신새끼", "씹새꺄",
            "개소리하네", "뻐큐",
            "fuck", "shit", "bitch", "asshole", "damn", "bastard", "dick", "cunt"
    );

    public static boolean contains(String content) {
        if (content == null) return false;
        String lower = content.toLowerCase();
        return Bad_words.stream().anyMatch(lower::contains);
    }
}
