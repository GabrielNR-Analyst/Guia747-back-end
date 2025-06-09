# Guia747

## Requirements

To run this project, ensure you have the following installed on your system:

- **Java Development Kit (JDK) 21** Required to build and run the application.
- **Docker** Used for containerized services or running the application in a Dockerized environment.

## Test Coverage with JaCoCo

This project uses [JaCoCo](https://www.eclemma.org/jacoco/) (Java Code Coverage) to generate test coverage reports, allowing you to monitor which parts of the code are coverage by tests and ensure system quality.

1. Run the tests: `./gradlew test`
2. Generate the coverage report: `./gradlew jacocoTestReport`
3. Open the reported located at `build/reports/jacoco/jacocoHtml/index.html`.

## Checkstyle

Checkstyle enforces coding standards by validating the source code against a set of predefined rules.
The rules are configured in the file located at `config/checkstyle/checkstyle.xml` in the root of the project.

To run Checkstyle and verify code compliance:

```shell
./gradlew checkstyleMain
./gradlew checkstyleTest
```

The results will be available in the `build/reports/checkstyle` directory of each subproject.
Both XML and HTML reports are generated to provide detailed insights into any violations detected.

Checkstyle helps maintain consistent code style, making the codebase easier to read and reducing potential errors caused by style inconsistencies.
