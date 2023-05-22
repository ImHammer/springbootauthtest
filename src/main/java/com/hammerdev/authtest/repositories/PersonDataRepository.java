package com.hammerdev.authtest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hammerdev.authtest.entities.PersonData;

public interface PersonDataRepository extends JpaRepository<PersonData, Long> {
	
}
