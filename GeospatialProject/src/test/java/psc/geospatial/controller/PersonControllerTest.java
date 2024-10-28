package psc.geospatial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import psc.geospatial.entity.Person;
import psc.geospatial.service.PersonService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Person person;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        person = new Person(1L, "José da Silva", LocalDate.of(2000, 4, 6), LocalDate.of(2020, 5, 10));
    }

    @Test
    void testListPeople() throws Exception {
        when(personService.listPeople()).thenReturn(List.of(person));
        mockMvc.perform(get("/api/person"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("José da Silva"));
        verify(personService, times(1)).listPeople();
    }

    @Test
    void testGetPersonById() throws Exception {
        when(personService.getPersonById(1L)).thenReturn(person);
        mockMvc.perform(get("/api/person/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("José da Silva"));
        verify(personService, times(1)).getPersonById(1L);
    }

    @Test
    void testCreatePerson() throws Exception {
        when(personService.createPerson(any(Person.class))).thenReturn(person);
        mockMvc.perform(post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("José da Silva"));
        verify(personService, times(1)).createPerson(any(Person.class));
    }

    @Test
    void testDeletePerson() throws Exception {
        doNothing().when(personService).deletePerson(1L);
        mockMvc.perform(delete("/api/person/1"))
                .andExpect(status().isOk());
        verify(personService, times(1)).deletePerson(1L);
    }

    @Test
    void testUpdatePerson() throws Exception {
        when(personService.updatePerson(eq(1L), any(Person.class))).thenReturn(person);
        mockMvc.perform(put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("José da Silva"));
        verify(personService, times(1)).updatePerson(eq(1L), any(Person.class));
    }

    @Test
    void testPatchPerson() throws Exception {
        Map<String, Object> updates = Map.of("name", "Updated Name");
        person.setName("Updated Name");
        when(personService.patchPerson(eq(1L), anyMap())).thenReturn(person);

        mockMvc.perform(patch("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
        verify(personService, times(1)).patchPerson(eq(1L), anyMap());
    }

    @Test
    void testCalculateAge() throws Exception {
        when(personService.calculateAge(1L, "days", LocalDate.now())).thenReturn(8342L);
        mockMvc.perform(get("/api/person/1/age")
                        .param("output", "days"))
                .andExpect(status().isOk())
                .andExpect(content().string("8342"));
        verify(personService, times(1)).calculateAge(1L, "days", LocalDate.now());
    }

    @Test
    void testCalculateSalaryFull() throws Exception {
        when(personService.calculateSalary(1L, "full", LocalDate.now())).thenReturn(new BigDecimal("3259.36"));
        mockMvc.perform(get("/api/person/1/salary")
                        .param("output", "full"))
                .andExpect(status().isOk())
                .andExpect(content().string("3259.36"));
        verify(personService, times(1)).calculateSalary(1L, "full", LocalDate.now());
    }

    @Test
    void testCalculateSalaryMin() throws Exception {
        when(personService.calculateSalary(1L, "min", LocalDate.now())).thenReturn(new BigDecimal("2.51"));
        mockMvc.perform(get("/api/person/1/salary")
                        .param("output", "min"))
                .andExpect(status().isOk())
                .andExpect(content().string("2.51"));
        verify(personService, times(1)).calculateSalary(1L, "min", LocalDate.now());
    }
}
