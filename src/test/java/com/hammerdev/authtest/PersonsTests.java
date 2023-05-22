package com.hammerdev.authtest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hammerdev.authtest.data.person.PersonCreateRequestDTO;
import com.hammerdev.authtest.data.person.PersonDataCreateDTO;
import com.hammerdev.authtest.entities.Person;
import com.hammerdev.authtest.services.person.PersonService;

@SpringBootTest
public class PersonsTests
{
	@Autowired
	PersonService personService;

	Person createdPerson;

	// @Test
	void createPersonWithUsernameFieldNull()
	{
		PersonCreateRequestDTO dto = new PersonCreateRequestDTO(
			null,
			"My First Name",
			"My Last Name",
			"mypassword123"
		);

		assertNotNull(assertThrows(Throwable.class, () -> personService.createPerson(dto)), "ERROR: Passou com username Nulo");
	}

	@Test
	void createPersonWithAllData()
	{
		PersonCreateRequestDTO dto = new PersonCreateRequestDTO(
			"myusername",
			"Ana",
			"Carolina",
			"mypassword@"
		);

		createdPerson = assertDoesNotThrow(() -> personService.createPerson(dto), "Não criou a pessoa");
	}

	@Test
	void createPersonDataAndAddress()
	{
		PersonDataCreateDTO dataDto = new PersonDataCreateDTO(
			1l,
			"kdsjkf",
			"sdlafk"
		);

		assertDoesNotThrow(() -> personService.createPersonData(dataDto), "Não criou a data");
	}
}
