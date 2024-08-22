package com.example.spring_boot_demo.controller;

import com.example.spring_boot_demo.model.Person;
import com.example.spring_boot_demo.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddPerson() throws Exception {
        Person person = new Person("Billy", 22);
        mockMvc.perform(MockMvcRequestBuilders.post("/person/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Person added successfully"))
                .andDo(print());
    }

    @Test
    void testGetAllPeople() throws Exception {
        Person person1 = new Person("Billy", 22);
        Person person2 = new Person("Zhar", 23);
        List<Person> people = Arrays.asList(person1, person2);

        when(personService.getAllPeople()).thenReturn(people);

        mockMvc.perform(MockMvcRequestBuilders.get("/person/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Billy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Zhar"))
                .andDo(print());
    }

    @Test
    void testGetNameToAgeMap() throws Exception {
        Map<String, Integer> nameToAgeMap = new HashMap<>();
        nameToAgeMap.put("Billy", 22);
        nameToAgeMap.put("Zhar", 23);

        when(personService.getNameToAgeMap()).thenReturn(nameToAgeMap);

        mockMvc.perform(MockMvcRequestBuilders.get("/person/nameToAgeMap")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Billy").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Zhar").value(23))
                .andDo(print());
    }

    @Test
    void testGetNamesOfAdults() throws Exception {
        List<String> adultNames = Arrays.asList("Billy", "Zhar");

        when(personService.getNamesOfAdults()).thenReturn(adultNames);

        mockMvc.perform(MockMvcRequestBuilders.get("/person/adults")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Billy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value("Zhar"))
                .andDo(print());
    }
}
