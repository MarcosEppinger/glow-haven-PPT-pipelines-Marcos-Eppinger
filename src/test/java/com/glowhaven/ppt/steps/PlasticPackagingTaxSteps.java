package com.glowhaven.ppt.steps;

import com.glowhaven.ppt.PptRegistrationChecker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

public class PlasticPackagingTaxSteps {

    private final PptRegistrationChecker checker = new PptRegistrationChecker();

    @Given("Glow Haven has imported {double} kg of plastic packaging in the period {word} {word} {int} to {word} {word} {int}")
    public void glowHavenHasImportedKgInPeriod(double weightKg, String startDay, String startMonth, int startYear,
                                               String endDay, String endMonth, int endYear) {
        checker.setImportedWeightKg(weightKg);
    }

    @When("a registration check is made on {word} {word} {int}")
    public void aRegistrationCheckIsMadeOn(String day, String month, int year) {
        int dayOfMonth = parseOrdinalDay(day);
        Month monthEnum = parseMonth(month);
        checker.setCheckDate(LocalDate.of(year, monthEnum, dayOfMonth));
    }

    @Then("Glow Haven must register for Plastic Packaging Tax")
    public void glowHavenMustRegister() {
        Assertions.assertTrue(checker.mustRegister(),
                "Glow Haven should be required to register for Plastic Packaging Tax");
    }

    @And("the registration deadline is {int} {word} {int}")
    public void theRegistrationDeadlineIs(int day, String month, int year) {
        LocalDate expectedDeadline = LocalDate.of(year, parseMonth(month), day);
        Assertions.assertEquals(Optional.of(expectedDeadline), checker.getRegistrationDeadline(),
                "Registration deadline should be " + expectedDeadline);
    }

    @Then("Glow Haven is not required to register for Plastic Packaging Tax")
    public void glowHavenIsNotRequiredToRegister() {
        Assertions.assertFalse(checker.mustRegister(),
                "Glow Haven should not be required to register for Plastic Packaging Tax");
    }

    @Then("Glow Haven is expected to be required to register for Plastic Packaging Tax at the next formal check")
    public void glowHavenIsExpectedToBeRequiredToRegister() {
        Assertions.assertTrue(checker.exceedsThreshold(),
                "Glow Haven's imports should exceed the registration threshold");
        Assertions.assertFalse(checker.isFormalCheck(),
                "The check should not be a formal check (not the last day of the month)");
    }

    @But("no formal requirement to register is yet in place.")
    public void noFormalRequirementToRegister() {
        Assertions.assertFalse(checker.mustRegister(),
                "There should be no formal requirement to register yet");
    }

    private int parseOrdinalDay(String ordinal) {
        return Integer.parseInt(ordinal.replaceAll("(?<=\\d)(st|nd|rd|th)$", ""));
    }

    private Month parseMonth(String monthName) {
        for (Month month : Month.values()) {
            if (month.getDisplayName(TextStyle.FULL, Locale.ENGLISH).equalsIgnoreCase(monthName)) {
                return month;
            }
        }
        throw new IllegalArgumentException("Unknown month: " + monthName);
    }
}
