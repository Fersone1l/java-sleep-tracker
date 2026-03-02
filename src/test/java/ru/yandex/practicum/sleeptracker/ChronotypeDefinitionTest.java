package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChronotypeDefinitionTest {
    ChronotypeDefinition chronotypeDefinition = new ChronotypeDefinition();

    private SleepingSession createSession(int day1, int hours1, int day2, int hours2) {
        return new SleepingSession(
                LocalDateTime.of(2025, 1, day1, hours1, 0),
                LocalDateTime.of(2025, 1, day2, hours2, 0),
                SleepQuality.NORMAL
        );
    }

    @Test
    void shouldReturnOwl() {
        List<SleepingSession> sessions = List.of(
                createSession(1, 23, 2, 10),
                createSession(3, 0, 3, 10),
                createSession(3, 23, 4, 10),
                createSession(5, 0, 5, 11)
        );

        SleepAnalysisResult result = chronotypeDefinition.apply(sessions);

        assertEquals("Хронотип", result.getFunctionTitle());
        assertEquals("сова", result.getResult());
    }

    @Test
    void shouldReturnLark() {
        List<SleepingSession> sessions = List.of(
                createSession(1, 21, 2, 6),
                createSession(3, 20, 3, 5),
                createSession(3, 22, 4, 6),
                createSession(5, 20, 5, 6)
        );

        SleepAnalysisResult result = chronotypeDefinition.apply(sessions);

        assertEquals("Хронотип", result.getFunctionTitle());
        assertEquals("жаворонок", result.getResult());
    }

    @Test
    void shouldReturnPigeon() {
        List<SleepingSession> sessions = List.of(
                createSession(1, 21, 2, 9),
                createSession(3, 0, 3, 5),
                createSession(3, 22, 4, 8),
                createSession(5, 23, 5, 6)
        );

        SleepAnalysisResult result = chronotypeDefinition.apply(sessions);

        assertEquals("Хронотип", result.getFunctionTitle());
        assertEquals("голубь", result.getResult());
    }

}