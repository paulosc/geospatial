package psc.geospatial.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private PersonService personService;
    private static final LocalDate CURRENT_DATE = LocalDate.of(2023, 2, 7);

    @BeforeEach
    void setUp() {
        personService = new PersonService();
    }

    @Test
    void testCalculateAgeInDays() {
        long expectedDays = 8342;
        long actualDays = personService.calculateAge(1L, "days", CURRENT_DATE);
        assertEquals(expectedDays, actualDays, "Age in days calculation failed");
    }

    @Test
    void testCalculateAgeInMonths() {
        long expectedMonths = 274;
        long actualMonths = personService.calculateAge(1L, "months", CURRENT_DATE);
        assertEquals(expectedMonths, actualMonths, "Age in months calculation failed");
    }

    @Test
    void testCalculateAgeInYears() {
        long expectedYears = 22;
        long actualYears = personService.calculateAge(1L, "years", CURRENT_DATE);
        assertEquals(expectedYears, actualYears, "Age in years calculation failed");
    }

    @Test
    void testCalculateSalaryFull() {
        BigDecimal expectedFullSalary = new BigDecimal("3259.36");
        BigDecimal actualFullSalary = personService.calculateSalary(1L, "full", CURRENT_DATE);
        assertEquals(expectedFullSalary, actualFullSalary, "Full salary calculation failed");
    }

    @Test
    void testCalculateSalaryInMinimumWages() {
        BigDecimal expectedMinWages = new BigDecimal("2.51");
        BigDecimal actualMinWages = personService.calculateSalary(1L, "min", CURRENT_DATE);
        assertEquals(expectedMinWages, actualMinWages, "Salary in minimum wages calculation failed");
    }

    @Test
    void testPersonNotFound() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            personService.getPersonById(99L);
        });
        assertEquals("404 NOT_FOUND \"Person not found\"", exception.getMessage());
    }

    @Test
    void testInvalidAgeOutputFormat() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            personService.calculateAge(1L, "invalidFormat", CURRENT_DATE);
        });
        assertEquals("400 BAD_REQUEST \"Invalid output format for age\"", exception.getMessage());
    }

    @Test
    void testInvalidSalaryOutputFormat() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            personService.calculateSalary(1L, "invalidFormat", CURRENT_DATE);
        });
        assertEquals("400 BAD_REQUEST \"Invalid output format for salary\"", exception.getMessage());
    }
}
