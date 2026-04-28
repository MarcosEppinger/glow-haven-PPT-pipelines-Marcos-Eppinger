# glow-haven-ppt

A REST API that helps Glow Haven determine whether they are required to register to pay **Plastic Packaging Tax (PPT)** in the UK.

## Overview

Under UK regulations, businesses that import more than 10 tonnes (10,000 kg) of plastic packaging within a 12-month period must register to pay Plastic Packaging Tax. This application automates that compliance check.

Given the weight of plastic packaging imported over a date range, the API evaluates:

- Whether the total import weight exceeds the 10-tonne registration threshold.
- Whether a registration check is **formal** (made on the last day of a calendar month) or **informational** (made on any other day).
- If registration is required, the **registration deadline**, which falls 30 days after the formal check date.

## Key Business Rules

| Rule | Detail |
|------|--------|
| Registration threshold | 10,000 kg of imported plastic packaging in the last 12 calendar months |
| Formal check | Check performed on the last day of a calendar month |
| Registration deadline | 30 days from the date of a formal check |

## Tech Stack

- **Language:** Java 17
- **REST Framework:** Spark Java
- **Testing:** Cucumber (BDD) + JUnit 5
- **Build Tool:** Maven

## Building and Running

```bash
mvn package
java -jar target/ppt-*.jar
```

The API server starts on port **8080**.

## Running Tests

```bash
mvn test
```

Cucumber HTML reports are generated at `target/cucumber-reports/cucumber.html`.
