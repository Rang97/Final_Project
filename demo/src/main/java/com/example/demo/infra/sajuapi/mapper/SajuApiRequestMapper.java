package com.example.demo.infra.sajuapi.mapper;

import com.example.demo.domain.saju.entity.CalendarType;
import com.example.demo.domain.saju.entity.SajuInput;
import com.example.demo.domain.user.entity.BirthTimeBranch;
import com.example.demo.infra.sajuapi.dto.SajuApiRequest;
import org.springframework.stereotype.Component;

@Component
public class SajuApiRequestMapper {

    public SajuApiRequest from(SajuInput input) {
        BirthTime birthTime = toBirthTime(input.getBirthTimeBranch());

        return SajuApiRequest.of(
                input.getBirthDate().getYear(),
                input.getBirthDate().getMonthValue(),
                input.getBirthDate().getDayOfMonth(),
                birthTime.hour(),
                birthTime.minute(),
                input.getCalendarType() == CalendarType.LUNAR,
                input.getGender()
        );
    }

    private BirthTime toBirthTime(BirthTimeBranch branch) {
        if (branch == null || branch == BirthTimeBranch.UNKNOWN) {
            return new BirthTime(null, null);
        }

        int hour = switch (branch) {
            case JA -> 0;
            case CHUK -> 2;
            case IN -> 4;
            case MYO -> 6;
            case JIN -> 8;
            case SA -> 10;
            case O -> 12;
            case MI -> 14;
            case SIN -> 16;
            case YU -> 18;
            case SUL -> 20;
            case HAE -> 22;
            case UNKNOWN -> throw new IllegalStateException("시간 미상은 앞에서 처리됩니다.");
        };

        return new BirthTime(hour, 30);
    }

    private record BirthTime(Integer hour, Integer minute) {
    }
}
