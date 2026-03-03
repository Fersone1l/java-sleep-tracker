package ru.yandex.practicum.sleeptracker;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {
    public static final String SEPARATOR = ";";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    public static final List<Function<List<SleepingSession>, SleepAnalysisResult>> ANALYTIC_FUNCTIONS = List.of(
            new SessionsCounter(),
            new MinimalSessionDuration(),
            new MaximalSessionDuration(),
            new AverageSessionDuration(),
            new BadSessionsCount(),
            new NightsWithoutSleepCount(),
            new ChronotypeDefinition()
    );

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Ошибка! Укажите путь к файлу с данными сна.");
                return;
            }

            String sleepLogFile = args[0];
            SleepLogLoader loader = new SleepLogLoader();
            List<SleepingSession> sessions = loader.loadFromFile(sleepLogFile, FORMATTER, SEPARATOR);


            List<SleepAnalysisResult> results = analyzeSessions(sessions);

            results.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<SleepAnalysisResult> analyzeSessions(List<SleepingSession> sessions) {
        return ANALYTIC_FUNCTIONS.stream()
                .map(function -> function.apply(sessions))
                .toList();
    }
}