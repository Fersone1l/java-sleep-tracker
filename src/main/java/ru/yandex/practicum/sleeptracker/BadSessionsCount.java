package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadSessionsCount implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public static final String TITLE = "Количество сессий с плохим качеством сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult(TITLE,
                sessions.stream()
                        .filter(session -> session.quality().equals(SleepQuality.BAD))
                                .toList()
                        .size());
    }
}
