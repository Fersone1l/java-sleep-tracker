package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SleepLogLoader {


    public List<SleepingSession> loadFromFile(String fileName, DateTimeFormatter formatter, String separator) throws IOException {
        File sleepLogFile = getFile(fileName);
        return readFile(sleepLogFile, formatter, separator);
    }

    public File getFile(String fileName) throws FileNotFoundException {
        Path path = Paths.get(fileName);
        File file = path.toFile();

        if (file.exists()) {
            return file;
        } else {
            throw new FileNotFoundException(String.format("Файла с именем %s не существует.", fileName));
        }
    }

    public List<SleepingSession> readFile(File file, DateTimeFormatter formatter, String separator) throws IOException {
        List<SleepingSession> sessions;
        try (FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(fileReader)) {
            sessions = reader.lines()
                    .map((String line) -> parseLine(line, formatter, separator))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
            if (sessions.isEmpty()) {
                System.out.println("Прочитан пустой файл " + file.getName());
            }
        } catch (IOException exception) {
            System.out.println("Произошла ошибка при чтении из файла" + file.getName());
            throw exception;
        }
        return sessions;
    }

    public Optional<SleepingSession> parseLine(String rawLineData, DateTimeFormatter formatter, String separator) {
        try {
            String[] parts = rawLineData.split(separator);

            if (parts.length < 3) {
                return Optional.empty();
            }

            LocalDateTime start = LocalDateTime.parse(parts[0].trim(), formatter);
            LocalDateTime end = LocalDateTime.parse(parts[1].trim(), formatter);
            SleepQuality quality = SleepQuality.valueOf(parts[2].trim().toUpperCase());
            if (start.isAfter(end)) {
                end = end.plusDays(1);
            }
            return Optional.of(new SleepingSession(start, end, quality));
        } catch (IllegalArgumentException | DateTimeParseException e) {
            return Optional.empty();
        }
    }
}
