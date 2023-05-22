package com.hammerdev.authtest.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hammerdev.authtest.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long>
{
    public Optional<Person> findByUsername(String username);
}
