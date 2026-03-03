package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SessionsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String TITLE = "Количество сессий сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Integer quantity = sessions.size();
        return new SleepAnalysisResult(TITLE, quantity);
    }
}
