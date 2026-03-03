package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;

public class AverageSessionDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String TITLE = "Средняя продолжительность сессии сна (в минутах)";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        OptionalDouble averageSleepingSession = sessions.stream()
                .mapToDouble(session ->
                        Duration.between(session.start(), session.end()).toMinutes())
                .average();

        if (averageSleepingSession.isPresent()) {

            return new SleepAnalysisResult(TITLE, String.format("%.0f минут", averageSleepingSession.getAsDouble()));
        } else {
            return new SleepAnalysisResult(TITLE, "не найдена");
        }
    }
}
