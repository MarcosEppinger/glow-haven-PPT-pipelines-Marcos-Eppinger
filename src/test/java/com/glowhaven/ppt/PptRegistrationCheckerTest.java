package com.glowhaven.ppt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PptRegistrationCheckerTest {

    private PptRegistrationChecker checker;

    @BeforeEach
    void setUp() {
        checker = new PptRegistrationChecker();
    }

    @Test
    void exceedsThresholdReturnsFalseWhenWeightIsBelowThreshold() {
        checker.setImportedWeightKg(9999.9);
        assertFalse(checker.exceedsThreshold());
    }

    @Test
    void exceedsThresholdReturnsTrueWhenWeightIsExactlyAtThreshold() {
        checker.setImportedWeightKg(1000.0);
        assertTrue(checker.exceedsThreshold());
    }

    @Test
    void exceedsThresholdReturnsTrueWhenWeightIsAboveThreshold() {
        checker.setImportedWeightKg(11000.0);
        assertTrue(checker.exceedsThreshold());
    }

    @Test
    void isFormalCheckThrowsIllegalStateExceptionWhenCheckDateNotSet() {
        assertThrows(IllegalStateException.class, () -> checker.isFormalCheck());
    }

    @Test
    void isFormalCheckReturnsTrueWhenCheckDateIsLastDayOf31DayMonth() {
        checker.setCheckDate(LocalDate.of(2026, 3, 31));
        assertTrue(checker.isFormalCheck());
    }

    @Test
    void isFormalCheckReturnsFalseWhenCheckDateIsNotLastDayOfMonth() {
        checker.setCheckDate(LocalDate.of(2026, 4, 10));
        assertFalse(checker.isFormalCheck());
    }

    @Test
    void isFormalCheckReturnsTrueWhenCheckDateIsLastDayOf30DayMonth() {
        checker.setCheckDate(LocalDate.of(2026, 4, 30));
        assertTrue(checker.isFormalCheck());
    }

    @Test
    void isFormalCheckReturnsFalseWhenCheckDateIsNotLastDayOf30DayMonth() {
        checker.setCheckDate(LocalDate.of(2026, 4, 29));
        assertFalse(checker.isFormalCheck());
    }

    @Test
    void isFormalCheckReturnsTrueWhenCheckDateIsLastDayOfFebruaryInNonLeapYear() {
        checker.setCheckDate(LocalDate.of(2025, 2, 28));
        assertTrue(checker.isFormalCheck());
    }

    @Test
    void isFormalCheckReturnsTrueWhenCheckDateIsLastDayOfFebruaryInLeapYear() {
        checker.setCheckDate(LocalDate.of(2024, 2, 29));
        assertTrue(checker.isFormalCheck());
    }

    @Test
    void mustRegisterReturnsTrueWhenThresholdExceededAndFormalCheck() {
        checker.setImportedWeightKg(10000.0);
        checker.setCheckDate(LocalDate.of(2026, 3, 31));
        assertTrue(checker.mustRegister());
    }

    @Test
    void mustRegisterReturnsFalseWhenThresholdExceededButNotFormalCheck() {
        checker.setImportedWeightKg(10000.0);
        checker.setCheckDate(LocalDate.of(2026, 4, 10));
        assertFalse(checker.mustRegister());
    }

    @Test
    void mustRegisterReturnsFalseWhenFormalCheckButThresholdNotExceeded() {
        checker.setImportedWeightKg(9999.9);
        checker.setCheckDate(LocalDate.of(2026, 3, 31));
        assertFalse(checker.mustRegister());
    }

    @Test
    void mustRegisterReturnsFalseWhenNeitherThresholdExceededNorFormalCheck() {
        checker.setImportedWeightKg(9999.9);
        checker.setCheckDate(LocalDate.of(2026, 4, 10));
        assertFalse(checker.mustRegister());
    }

    @Test
    void getRegistrationDeadlineReturnsDeadline30DaysAfterCheckDateWhenMustRegister() {
        checker.setImportedWeightKg(10000.0);
        checker.setCheckDate(LocalDate.of(2026, 3, 31));
        assertEquals(Optional.of(LocalDate.of(2026, 4, 30)), checker.getRegistrationDeadline());
    }

    @Test
    void getRegistrationDeadlineReturnsEmptyWhenThresholdNotExceeded() {
        checker.setImportedWeightKg(9999.9);
        checker.setCheckDate(LocalDate.of(2026, 3, 31));
        assertEquals(Optional.empty(), checker.getRegistrationDeadline());
    }

    @Test
    void getRegistrationDeadlineReturnsEmptyWhenNotFormalCheck() {
        checker.setImportedWeightKg(10000.0);
        checker.setCheckDate(LocalDate.of(2026, 4, 10));
        assertEquals(Optional.empty(), checker.getRegistrationDeadline());
    }
}
