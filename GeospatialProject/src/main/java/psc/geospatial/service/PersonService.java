package psc.geospatial.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import psc.geospatial.entity.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class PersonService {
    private static final BigDecimal INITIAL_SALARY = BigDecimal.valueOf(1558.00);
    private static final BigDecimal MINIMUM_WAGE = BigDecimal.valueOf(1302.00);

    private final Map<Long, Person> personMap = new HashMap<>();

    public PersonService() {
        personMap.put(1L, new Person(1L, "José da Silva", LocalDate.of(2000, 4, 6), LocalDate.of(2020, 5, 10)));
        personMap.put(2L, new Person(2L, "Maria Oliveira", LocalDate.of(1995, 7, 15), LocalDate.of(2019, 3, 1)));
        personMap.put(3L, new Person(3L, "Carlos Pereira", LocalDate.of(1988, 12, 1), LocalDate.of(2015, 8, 20)));
    }

    public List<Person> listPeople() {
        return personMap.values().stream()
                .sorted(Comparator.comparing(Person::getName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public Person getPersonById(Long id) {
        return Optional.ofNullable(personMap.get(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
    }

    public Person createPerson(Person person) {
        if (person.getId() == null) {
            person.setId(personMap.keySet().stream().max(Long::compareTo).orElse(0L) + 1);
        } else if (personMap.containsKey(person.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ID already exists");
        }
        personMap.put(person.getId(), person);
        return person;
    }

    public void deletePerson(Long id) {
        if (personMap.remove(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        Person existingPerson = getPersonById(id);
        existingPerson.setName(updatedPerson.getName());
        existingPerson.setBirthDate(updatedPerson.getBirthDate());
        existingPerson.setAdmissionDate(updatedPerson.getAdmissionDate());
        return existingPerson;
    }

    public Person patchPerson(Long id, Map<String, Object> updates) {
        Person person = getPersonById(id);

        updates.forEach((attribute, value) -> {
            switch (attribute) {
                case "name" -> person.setName((String) value);
                case "birthDate" -> person.setBirthDate(LocalDate.parse((String) value));
                case "admissionDate" -> person.setAdmissionDate(LocalDate.parse((String) value));
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid attribute for update");
            }
        });

        return person;
    }

    public long calculateAge(Long id, String output, LocalDate now) {
        Person person = getPersonById(id);
        LocalDate birthDate = person.getBirthDate();

        return switch (output.toLowerCase()) {
            case "days" -> ChronoUnit.DAYS.between(birthDate, now);
            case "months" -> ChronoUnit.MONTHS.between(birthDate, now);
            case "years" -> ChronoUnit.YEARS.between(birthDate, now);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid output format for age");
        };
    }

    public BigDecimal calculateSalary(Long id, String output, LocalDate now) {
        Person person = getPersonById(id);
        LocalDate admissionDate = person.getAdmissionDate();

        BigDecimal salary = INITIAL_SALARY;

        long yearsWorked = ChronoUnit.YEARS.between(admissionDate, now);

        for (int i = 0; i < yearsWorked; i++) {
            salary = salary.multiply(BigDecimal.valueOf(1.18)).add(BigDecimal.valueOf(500.00));
        }

        if ("min".equalsIgnoreCase(output)) {
            return salary.divide(MINIMUM_WAGE, 2, RoundingMode.UP);
        } else if ("full".equalsIgnoreCase(output)) {
            return salary.setScale(2, RoundingMode.UP);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid output format for salary");
        }
    }

}

