package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionsCounterTest {
    private final SessionsCounter counter = new SessionsCounter();

    @Test
    void shouldReturnCorrectSessionCountOfThreeSessions() {

        List<SleepingSession> sessions = List.of(
                createSession(1),
                createSession(2),
                createSession(3)
        );

        SleepAnalysisResult result = counter.apply(sessions);

        assertEquals(3, result.getResult());
        assertEquals("Количество сессий сна", result.getFunctionTitle());
    }

    @Test
    void shouldReturnCorrectSessionCountOfZeroSessions() {

        List<SleepingSession> sessions = List.of();

        SleepAnalysisResult result = counter.apply(sessions);

        assertEquals(0, result.getResult());
        assertEquals("Количество сессий сна", result.getFunctionTitle());
    }

    private SleepingSession createSession(int day) {
        return new SleepingSession(
                LocalDateTime.of(2025, 1, day, 22, 0),
                LocalDateTime.of(2025, 1, day + 1, 6, 0),
                SleepQuality.NORMAL
        );
    }

}