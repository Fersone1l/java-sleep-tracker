package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NightsWithoutSleepCountTest {
    public final NightsWithoutSleepCount nightsWithoutSleepCount = new NightsWithoutSleepCount();

    private SleepingSession createSession(int day1, int hours1, int day2, int hours2) {
        return new SleepingSession(
                LocalDateTime.of(2025, 1, day1, hours1, 0),
                LocalDateTime.of(2025, 1, day2, hours2, 0),
                SleepQuality.NORMAL
        );
    }

    @Test
    void shouldReturnCorrectCountNightsWithoutSleepOfZeroNightsWithoutSleep() {
        List<SleepingSession> sessions = List.of(
                createSession(1, 22, 2, 6),
                createSession(2, 22, 3, 6),
                createSession(3, 22, 4, 6)
        );

        SleepAnalysisResult result = nightsWithoutSleepCount.apply(sessions);

        assertEquals("Количество ночей без сна", result.getFunctionTitle());
        assertEquals(0L, result.getResult());
    }

    @Test
    void shouldReturnCorrectCountNightsWithoutSleepOfOneNightsWithoutSleep() {
        List<SleepingSession> sessions = List.of(
                createSession(1, 22, 2, 6),
                createSession(2, 22, 3, 6),
                createSession(4, 22, 5, 6)
        );

        SleepAnalysisResult result = nightsWithoutSleepCount.apply(sessions);

        assertEquals("Количество ночей без сна", result.getFunctionTitle());
        assertEquals(1L, result.getResult());
    }

    @Test
    void shouldReturnCorrectCountNightsWithoutSleepOfOneNight() {
        List<SleepingSession> sessions = List.of(
                createSession(1, 22, 2, 6)
        );

        SleepAnalysisResult result = nightsWithoutSleepCount.apply(sessions);

        assertEquals("Количество ночей без сна", result.getFunctionTitle());
        assertEquals(0L, result.getResult());
    }

    @Test
    void shouldReturnCorrectCountNightsWithoutSleepOfOneDateWithShortDaySleepAndShortNightSleep() {
        List<SleepingSession> sessions = List.of(
                createSession(1, 22, 2, 6),
                createSession(2, 13, 2, 16),
                createSession(2, 21, 3, 0),
                createSession(3, 22, 4, 6)
        );

        SleepAnalysisResult result = nightsWithoutSleepCount.apply(sessions);

        assertEquals("Количество ночей без сна", result.getFunctionTitle());
        assertEquals(0L, result.getResult());
    }

    @Test
    void shouldReturnCorrectCountNightsWithoutSleepOfZeroSessions() {
        List<SleepingSession> sessions = List.of();

        SleepAnalysisResult result = nightsWithoutSleepCount.apply(sessions);

        assertEquals("Количество ночей без сна", result.getFunctionTitle());
        assertEquals(0L, result.getResult());
    }

    @Test
    void shouldReturnCorrectCountNightsWithoutSleepOfOneNightsWithoutSleepOfUnsortedSessions() {
        List<SleepingSession> sessions = List.of(
                createSession(1, 22, 2, 6),
                createSession(4, 22, 5, 6),
                createSession(2, 22, 3, 6)
        );

        SleepAnalysisResult result = nightsWithoutSleepCount.apply(sessions);

        assertEquals("Количество ночей без сна", result.getFunctionTitle());
        assertEquals(1L, result.getResult());
    }

}