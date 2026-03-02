package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeDefinition implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public static final String TITLE = "Хронотип";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Map<String, Long> chronotypeCount = sessions.stream()
                .filter(this::isNightSleep)
                .map(this::determineChronotype)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        long owlCount = chronotypeCount.getOrDefault("сова", 0L);
        long larkCount = chronotypeCount.getOrDefault("жаворонок", 0L);

        if (owlCount > larkCount) {
            return new SleepAnalysisResult(TITLE, "сова");
        } else if (larkCount > owlCount) {
            return new SleepAnalysisResult(TITLE, "жаворонок");
        } else {
            return new SleepAnalysisResult(TITLE, "голубь");
        }
    }

    private Optional<String> determineChronotype(SleepingSession session) {
        LocalDateTime start = session.start();
        LocalDateTime end = session.end();

        LocalDateTime owlStart = start.getDayOfYear() == end.getDayOfYear() ?
                LocalDateTime.of(start.toLocalDate().minusDays(1), LocalTime.of(23, 0))
                : LocalDateTime.of(start.toLocalDate(), LocalTime.of(23, 0));

        LocalDateTime owlEnd = LocalDateTime.of(end.toLocalDate(), LocalTime.of(9, 0));

        LocalDateTime larkStart = start.getDayOfYear() == end.getDayOfYear() ?
                LocalDateTime.of(start.toLocalDate().minusDays(1), LocalTime.of(22, 0))
                : LocalDateTime.of(start.toLocalDate(), LocalTime.of(22, 0));

        LocalDateTime larkEnd = LocalDateTime.of(end.toLocalDate(), LocalTime.of(7, 0));


        boolean isOwl = start.isAfter(owlStart) &&
                end.isAfter(owlEnd);

        boolean isLark = start.isBefore(larkStart) &&
                end.isBefore(larkEnd);

        if (isOwl) return Optional.of("сова");
        if (isLark) return Optional.of("жаворонок");
        return Optional.empty();
    }

    private boolean isNightSleep(SleepingSession session) {
        if (session.start().toLocalDate().equals(session.end().toLocalDate())) {
            LocalTime start = session.start().toLocalTime();
            LocalTime end = session.end().toLocalTime();
            LocalTime nightStart = LocalTime.of(0, 0);
            LocalTime nightEnd = LocalTime.of(6, 0);

            return !start.isBefore(nightStart) && !end.isAfter(nightEnd) ||
                    start.isBefore(nightEnd) && end.isAfter(nightStart);
        } else {
            return true;
        }
    }
}
