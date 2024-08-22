package com.example.spring_boot_demo.service;

import com.example.spring_boot_demo.model.Person;
import com.example.spring_boot_demo.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
public class PersonServiceTest {
   @Mock
    private PersonRepository personRepository;

   @InjectMocks
    private PersonService personService;

   @BeforeEach
    void setUp() {
       MockitoAnnotations.openMocks(this);
   }

    @Test
    void testAddPerson() {
        Person person = new Person("John Doe", 25);
        personService.addPerson(person.getName(), person.getAge());
        verify(personRepository, times(1)).save(person);
    }

   @Test
    void testGetAllPeople() {
       List<Person> personList = Arrays.asList(
               new Person("Billy", 22),
               new Person("Kurt", 19)
       );
       when(personRepository.findAll()).thenReturn(personList);

       List<Person> result = personService.getAllPeople();
       assertEquals(2, result.size());
       assertEquals("Billy", result.get(0).getName());
       assertEquals("Kurt", result.get(1).getName());
   }

    @Test
    void testGetNameToAgeMap() {
        List<Person> personList = Arrays.asList(
                new Person("Billy", 22),
                new Person("Kurt", 19)
        );
        when(personRepository.findAll()).thenReturn(personList);

        Map<String, Integer> nameToAgeMap = personService.getNameToAgeMap();
        assertEquals(2, nameToAgeMap.size());
        assertEquals(22, nameToAgeMap.get("Billy"));
        assertEquals(19, nameToAgeMap.get("Kurt"));
    }

    @Test
    void getNamesOfAdults() {
        List<Person> personList = Arrays.asList(
                new Person("Billy", 22),
                new Person("Kurt", 19),
                new Person("John", 16)
        );
        when(personRepository.findAll()).thenReturn(personList);

        List<String> result = personService.getNamesOfAdults();
        assertEquals(2, result.size());
        assertEquals("Billy", result.get(0));
        assertEquals("Kurt", result.get(1));
    }
}
