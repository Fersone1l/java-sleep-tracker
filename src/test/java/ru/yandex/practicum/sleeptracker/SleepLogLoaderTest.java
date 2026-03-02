package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SleepLogLoaderTest {
    @Test
    void shouldReturnFileIfExists(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("test.txt");
        Files.createFile(file);

        SleepLogLoader loader = new SleepLogLoader();
        File result = loader.getFile(file.toString());

        assertTrue(result.exists());
    }

    @Test
    void shouldThrowExceptionIfFileNotExists() {
        SleepLogLoader loader = new SleepLogLoader();

        assertThrows(FileNotFoundException.class,
                () -> loader.getFile("not_existing.txt"));
    }

    @Test
    void shouldParseOneLineCorrectly(@TempDir Path tempDir) throws Exception {
        SleepLogLoader loader = new SleepLogLoader();
        Path file = tempDir.resolve("test.txt");

        Files.writeString(file, "02.10.25 23:50;03.10.25 06:40;NORMAL\n", StandardCharsets.UTF_8);

        List<SleepingSession> sessions = loader.loadFromFile(
                file.toString(),
                SleepTrackerApp.FORMATTER,
                SleepTrackerApp.SEPARATOR
        );

        assertEquals(1, sessions.size());
    }

    @Test
    void shouldIgnoreInvalidLine(@TempDir Path tempDir) throws Exception {
        SleepLogLoader loader = new SleepLogLoader();
        Path file = tempDir.resolve("test.txt");

        Files.writeString(file, "aaaaaaaaa\n", StandardCharsets.UTF_8);

        List<SleepingSession> sessions = loader.loadFromFile(
                file.toString(),
                SleepTrackerApp.FORMATTER,
                SleepTrackerApp.SEPARATOR
        );

        assertEquals(0, sessions.size());
    }

    @Test
    void shouldIgnoreInvalidLineAndParseValidLines(@TempDir Path tempDir) throws Exception {
        SleepLogLoader loader = new SleepLogLoader();
        Path file = tempDir.resolve("test.txt");

        Files.writeString(file,
                """
                        01.01.25 22:00;02.01.25 06:00;GOOD
                        wrong data
                        02.01.25 22:00;03.01.25 06:00;BAD
                        """,
                StandardCharsets.UTF_8);

        List<SleepingSession> sessions = loader.loadFromFile(
                file.toString(),
                SleepTrackerApp.FORMATTER,
                SleepTrackerApp.SEPARATOR
        );

        assertEquals(2, sessions.size());
    }

    @Test
    void shouldAddDayIfEndBeforeStart(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("test.txt");

        Files.writeString(file,
                "01.01.25 23:00;01.01.25 06:00;GOOD\n",
                StandardCharsets.UTF_8);

        SleepLogLoader loader = new SleepLogLoader();

        List<SleepingSession> sessions =
                loader.loadFromFile(file.toString(),
                        SleepTrackerApp.FORMATTER,
                        SleepTrackerApp.SEPARATOR);

        SleepingSession session = sessions.get(0);

        assertTrue(session.end().isAfter(session.start()));
    }

    @Test
    void shouldReturnEmptyListForEmptyFile(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("test.txt");
        Files.createFile(file);

        SleepLogLoader loader = new SleepLogLoader();

        List<SleepingSession> sessions =
                loader.loadFromFile(file.toString(),
                        SleepTrackerApp.FORMATTER,
                        SleepTrackerApp.SEPARATOR);

        assertTrue(sessions.isEmpty());
    }
}