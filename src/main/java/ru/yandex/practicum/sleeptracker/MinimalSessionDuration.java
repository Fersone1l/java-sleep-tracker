package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MinimalSessionDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String TITLE = "Минимальная продолжительность сессии сна (в минутах)";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Optional<SleepingSession> minimalSleepingSession = sessions.stream()
                .min(Comparator.comparing(sleepingSession ->
                        Duration.between(sleepingSession.start(), sleepingSession.end())));

        if (minimalSleepingSession.isPresent()) {
            Duration duration = Duration.between(minimalSleepingSession.get().start(), minimalSleepingSession.get().end());
            return new SleepAnalysisResult(TITLE, duration.toMinutes() + " минут");
        } else {
            return new SleepAnalysisResult(TITLE, "не найдена");
        }
    }
}
