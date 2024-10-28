package psc.geospatial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import psc.geospatial.entity.Person;
import psc.geospatial.service.PersonService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> listPeople() {
        return personService.listPeople();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person updatedPerson) {
        return personService.updatePerson(id, updatedPerson);
    }

    @PatchMapping("/{id}")
    public Person patchPerson(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return personService.patchPerson(id, updates);
    }

    @GetMapping("/{id}/age")
    public long calculateAge(@PathVariable Long id, @RequestParam String output) {
        return personService.calculateAge(id, output, LocalDate.now());
    }

    @GetMapping("/{id}/salary")
    public BigDecimal calculateSalary(@PathVariable Long id, @RequestParam String output) {
        return personService.calculateSalary(id, output, LocalDate.now());
    }
}


