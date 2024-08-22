package com.example.spring_boot_demo.repository;

import com.example.spring_boot_demo.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testSaveAndFindPerson() {
        Person person = new Person("Billy", 22);
        personRepository.save(person);

        Person foundPerson = personRepository.findById(person.getId()).orElse(null);
        assertThat(foundPerson).isNotNull();
        assertThat(foundPerson.getName()).isEqualTo("Billy");
        assertThat(foundPerson.getAge()).isEqualTo(22);
    }

    @Test
    public void testDeletePerson() {
        Person person = new Person("Myell", 17);
        personRepository.save(person);
        personRepository.deleteById(person.getId());

        Person foundPerson = personRepository.findById(person.getId()).orElse(null);
        assertThat(foundPerson).isNull();
    }

    @Test
    public void testFindAllPeople() {
        Person person1 = new Person("Billy", 22);
        Person person2 = new Person("Zhar", 23);
        personRepository.save(person1);
        personRepository.save(person2);

        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(2);
    }

    @Test
    public void testFindByAgeGreaterThan() {
        Person person1 = new Person("John", 22);
        Person person2 = new Person("Mark", 35);
        Person person3 = new Person("Julian", 17);
        personRepository.save(person1);
        personRepository.save(person2);
        personRepository.save(person3);

        List<Person> result = personRepository.findByAgeGreaterThan(18);
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Person::getName).containsExactlyInAnyOrder("John", "Mark");
    }
}
