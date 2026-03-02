package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaximalSessionDurationTest {
    private final MaximalSessionDuration maximalSessionDuration = new MaximalSessionDuration();

    @Test
    void shouldReturnCorrectMaximalSessionDurationOfZeroSessions() {

        List<SleepingSession> sessions = List.of();

        SleepAnalysisResult result = maximalSessionDuration.apply(sessions);

        assertEquals("не найдена", result.getResult());
        assertEquals("Максимальная продолжительность сессии сна (в минутах)", result.getFunctionTitle());
    }

    @Test
    void shouldReturnCorrectMaximalSessionDurationOfThreeSessions() {

        List<SleepingSession> sessions = List.of(
                createSession(1),
                createSession(2),
                createSession(3)
        );

        SleepAnalysisResult result = maximalSessionDuration.apply(sessions);

        assertEquals("660 минут", result.getResult());
        assertEquals("Максимальная продолжительность сессии сна (в минутах)", result.getFunctionTitle());
    }

    private SleepingSession createSession(int hour) {
        return new SleepingSession(
                LocalDateTime.of(2025, 1, 1, 22, 0),
                LocalDateTime.of(2025, 1, 2, 6 + hour, 0),
                SleepQuality.NORMAL
        );
    }
}