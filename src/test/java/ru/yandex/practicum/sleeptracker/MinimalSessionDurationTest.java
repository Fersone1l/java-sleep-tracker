package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinimalSessionDurationTest {
    private final MinimalSessionDuration minimalSessionDuration = new MinimalSessionDuration();

    @Test
    void shouldReturnCorrectMinimalSessionDurationOfZeroSessions() {

        List<SleepingSession> sessions = List.of();

        SleepAnalysisResult result = minimalSessionDuration.apply(sessions);

        assertEquals("не найдена", result.getResult());
        assertEquals("Минимальная продолжительность сессии сна (в минутах)", result.getFunctionTitle());
    }

    @Test
    void shouldReturnCorrectMinimalSessionDurationOfThreeSessions() {

        List<SleepingSession> sessions = List.of(
                createSession(1),
                createSession(2),
                createSession(3)
        );

        SleepAnalysisResult result = minimalSessionDuration.apply(sessions);

        assertEquals("540 минут", result.getResult());
        assertEquals("Минимальная продолжительность сессии сна (в минутах)", result.getFunctionTitle());
    }

    private SleepingSession createSession(int hour) {
        return new SleepingSession(
                LocalDateTime.of(2025, 1, 1, 22, 0),
                LocalDateTime.of(2025, 1, 2, 6 + hour, 0),
                SleepQuality.NORMAL
        );
    }
}