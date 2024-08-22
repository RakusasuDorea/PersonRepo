package com.example.spring_boot_demo.repository;

import com.example.spring_boot_demo.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CustomPersonRepositoryImpl implements CustomPersonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Person> findByAgeGreaterThan(int age) {
        String jpql = "SELECT p FROM Person p WHERE p.age > :age";
        TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
        query.setParameter("age", age);
        return query.getResultList();
    }
}
