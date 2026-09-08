// global/util/CurrentUserProvider.java
package com.example.demo.global.util;

public interface CurrentUserProvider {
    Long getCurrentUserId();        // 로그인 필수인 곳 (없으면 예외)
    Long getCurrentUserIdOrNull();  // 로그인 선택인 곳 (없으면 null)
}