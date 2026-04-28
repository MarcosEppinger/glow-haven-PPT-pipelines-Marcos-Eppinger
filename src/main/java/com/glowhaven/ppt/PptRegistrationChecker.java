package com.glowhaven.ppt;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

public class PptRegistrationChecker {

    private static final double REGISTRATION_THRESHOLD_KG = 10000.0;

    private double importedWeightKg;
    private LocalDate checkDate;

    public void setImportedWeightKg(double weightKg) {
        this.importedWeightKg = weightKg;
    }

    public void setCheckDate(LocalDate checkDate) {
        this.checkDate = checkDate;
    }

    public boolean exceedsThreshold() {
        return importedWeightKg >= REGISTRATION_THRESHOLD_KG;
    }

    public boolean isFormalCheck() {
        if (checkDate == null) {
            throw new IllegalStateException("Check date has not been set");
        }
        YearMonth yearMonth = YearMonth.of(checkDate.getYear(), checkDate.getMonth());
        return checkDate.getDayOfMonth() == yearMonth.lengthOfMonth();
    }

    public boolean mustRegister() {
        return exceedsThreshold() && isFormalCheck();
    }

    public Optional<LocalDate> getRegistrationDeadline() {
        if (!mustRegister()) {
            return Optional.empty();
        }
        return Optional.of(checkDate.plusDays(30));
    }
}
