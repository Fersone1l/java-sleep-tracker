package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BadSessionsCountTest {
    private final BadSessionsCount badSessionsCount = new BadSessionsCount();

    private SleepingSession createSession(SleepQuality quality) {
        return new SleepingSession(
                LocalDateTime.of(2025, 1, 1, 22, 0),
                LocalDateTime.of(2025, 1, 2, 6, 0),
                quality
        );
    }

    @Test
    void shouldReturnCorrectCountBadSessionOfZeroBadSessions() {

        List<SleepingSession> sessions = List.of(
                createSession(SleepQuality.NORMAL),
                createSession(SleepQuality.GOOD),
                createSession(SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = badSessionsCount.apply(sessions);

        assertEquals(0, result.getResult());
        assertEquals("Количество сессий с плохим качеством сна", result.getFunctionTitle());
    }

    @Test
    void shouldReturnCorrectCountBadSessionOfThreeBadSessions() {

        List<SleepingSession> sessions = List.of(
                createSession(SleepQuality.BAD),
                createSession(SleepQuality.BAD),
                createSession(SleepQuality.BAD)
        );

        SleepAnalysisResult result = badSessionsCount.apply(sessions);

        assertEquals(3, result.getResult());
        assertEquals("Количество сессий с плохим качеством сна", result.getFunctionTitle());
    }

    @Test
    void shouldReturnCorrectCountBadSessionOfOneBadSessions() {

        List<SleepingSession> sessions = List.of(
                createSession(SleepQuality.GOOD),
                createSession(SleepQuality.NORMAL),
                createSession(SleepQuality.BAD)
        );

        SleepAnalysisResult result = badSessionsCount.apply(sessions);

        assertEquals(1, result.getResult());
        assertEquals("Количество сессий с плохим качеством сна", result.getFunctionTitle());
    }
}