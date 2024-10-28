package psc.geospatial.entity;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private LocalDate admissionDate;
}
