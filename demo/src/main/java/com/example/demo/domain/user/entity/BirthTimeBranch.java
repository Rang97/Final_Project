package com.example.demo.domain.user.entity;

public enum BirthTimeBranch {
    JA("자시", "23:30~01:29"),
    CHUK("축시", "01:30~03:29"),
    IN("인시", "03:30~05:29"),
    MYO("묘시", "05:30~07:29"),
    JIN("진시", "07:30~09:29"),
    SA("사시", "09:30~11:29"),
    O("오시", "11:30~13:29"),
    MI("미시", "13:30~15:29"),
    SIN("신시", "15:30~17:29"),
    YU("유시", "17:30~19:29"),
    SUL("술시", "19:30~21:29"),
    HAE("해시", "21:30~23:29"),
    UNKNOWN("시간대 모름", null);

    private final String label;
    private final String timeRange;

    BirthTimeBranch(String label, String timeRange) {
        this.label = label;
        this.timeRange = timeRange;
    }

    public String getLabel() {
        return label;
    }

    public String getTimeRange() {
        return timeRange;
    }
}
