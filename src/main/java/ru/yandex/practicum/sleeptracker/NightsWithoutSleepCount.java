package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public class NightsWithoutSleepCount implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public static final String TITLE = "Количество ночей без сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(TITLE, 0L);
        }

        List<LocalDate> nightsWithSleep = sessions.stream()
                .filter(this::isNightSleep)
                .map(this::getNightDate)
                .toList();

        List<LocalDate> allNightDates = sessions.stream()
                .map(this::getNightDate)
                .sorted()
                .toList();

        LocalDate firstDate = allNightDates.getFirst();
        LocalDate lastDate = allNightDates.getLast();

        long totalNights = ChronoUnit.DAYS.between(firstDate, lastDate) + 1;

        long nightsWithoutSleep = totalNights - nightsWithSleep.size();

        return new SleepAnalysisResult(TITLE, nightsWithoutSleep);
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

    private LocalDate getNightDate(SleepingSession session) {
        LocalTime midday = LocalTime.of(12, 0);
        if (session.start().toLocalTime().isAfter(midday)) {
            return session.start().toLocalDate().plusDays(1);
        } else {
            return session.start().toLocalDate();
        }
    }
}

