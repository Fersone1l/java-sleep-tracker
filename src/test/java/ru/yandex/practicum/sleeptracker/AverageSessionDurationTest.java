package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AverageSessionDurationTest {
    private final AverageSessionDuration averageSessionDuration = new AverageSessionDuration();

    @Test
    void shouldReturnCorrectAverageSessionDurationOfZeroSessions() {

        List<SleepingSession> sessions = List.of();

        SleepAnalysisResult result = averageSessionDuration.apply(sessions);

        assertEquals("не найдена", result.getResult());
        assertEquals("Средняя продолжительность сессии сна (в минутах)", result.getFunctionTitle());
    }

    @Test
    void shouldReturnCorrectAverageSessionDurationOfThreeSessions() {

        List<SleepingSession> sessions = List.of(
                createSession(1),
                createSession(2),
                createSession(3)
        );

        SleepAnalysisResult result = averageSessionDuration.apply(sessions);

        assertEquals("600,0 минут", result.getResult());
        assertEquals("Средняя продолжительность сессии сна (в минутах)", result.getFunctionTitle());
    }

    private SleepingSession createSession(int hour) {
        return new SleepingSession(
                LocalDateTime.of(2025, 1, 1, 22, 0),
                LocalDateTime.of(2025, 1, 2, 6 + hour, 0),
                SleepQuality.NORMAL
        );
    }

}