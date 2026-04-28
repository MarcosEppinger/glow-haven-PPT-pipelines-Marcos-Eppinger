Feature: Determine whether Glow Haven needs to register to pay Plastic Packaging Tax

  As Compliance Manager, I want to know whether Glow Haven must register to pay plastic
  packaging tax and when by, so that we can be sure to meet the deadline for
  registration.

  The current threshold for payment is 10 tonnes of imported plastic packaging within the last
  12 calendar months.  Registration must take place within 30 days of the formal date for checking,
  which is the last day of the month.  If a check is made on any day other than the last day of the
  month then the check is for information only and does not constitute a requirement to register.

  Scenario: Glow Haven must register when plastic packaging imports in the last 12 months exceed the threshold, and the check is made on the last day of the current month

    Given Glow Haven has imported 10000 kg of plastic packaging in the period 1st April 2025 to 31st March 2026
    When a registration check is made on 31st March 2026
    Then Glow Haven must register for Plastic Packaging Tax
    And the registration deadline is 30 April 2026

  Scenario: Glow Haven does not need to register when monthly plastic packaging imports are below the threshold, even though the check is made on the last day of the month.

    Given Glow Haven has imported 9999.9 kg of plastic packaging in the period 1st April 2025 to 31st March 2026
    When a registration check is made on 31st March 2026
    Then Glow Haven is not required to register for Plastic Packaging Tax

  Scenario: Registration checks for plastic packaging tax that are not made on the last day of the month are informational only.

    Given Glow Haven has imported 11000 kg of plastic packaging in the period 9th April 2025 to 10th April 2026
    When a registration check is made on 10th April 2026
    Then Glow Haven is expected to be required to register for Plastic Packaging Tax at the next formal check
    But no formal requirement to register is yet in place.
