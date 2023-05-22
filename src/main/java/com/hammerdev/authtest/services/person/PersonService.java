package com.hammerdev.authtest.services.person;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hammerdev.authtest.config.ApplicationConfig;
import com.hammerdev.authtest.data.person.PersonCreateRequestDTO;
import com.hammerdev.authtest.data.person.PersonDataCreateDTO;
import com.hammerdev.authtest.entities.Person;
import com.hammerdev.authtest.entities.PersonData;
import com.hammerdev.authtest.repositories.PersonDataRepository;
import com.hammerdev.authtest.repositories.PersonRepository;
import com.hammerdev.authtest.utils.Responses;
import com.hammerdev.authtest.utils.validations.ValidationFieldErroException;
import com.hammerdev.authtest.utils.validations.ValidationObject;

@Service
public class PersonService
{

	@Autowired
	private ApplicationConfig appConfig;

	@Autowired
	private PersonRepository personRepository;

    @Autowired
    private PersonDataRepository personDataRepository;

	public Person createPerson(PersonCreateRequestDTO dto) throws ResponseStatusException
	{
        // VALIDANDO OS CAMPOS
        ValidationObject validationFields = new ValidationObject();
        validationFields.string("username").minClear(6, "too short").maxClear(16, "too long").nonNull("is null");
        validationFields.string("firstName").minClear(3, "too short").maxClear(10, "too long").nonNull("is null");
        validationFields.reference("lastName", "firstName");
        validationFields.string("password").minClear(8, "too short").maxClear(32, "too long").nonNull("is null").pattern("^[\\w@#$!]+$", "bad characters");

        // HANDLED EXCEPTIONS
        try {
            validationFields.validateException(dto);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error", e);
        } catch (ValidationFieldErroException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getFieldName() + " " + e.getMessage(), e);
        }

        // VERIFICANDO EXISTẼNCIA DO USUARIO
        Optional<Person> existsUser = personRepository.findByUsername(dto.username());
        if (existsUser.isPresent()) {
            throw Responses.badRequest("Username in use!");
        }

        // "CRIPTOGRAFANDO" A SENHA
        String encryptedPassword = BCrypt.hashpw(dto.password(), BCrypt.gensalt(appConfig.getSaltRounds()));

        // CRIANDO A INSTANCIA DO USUARIO
        Person person = new Person();
        person.setUsername(dto.username());
        person.setFirstName(dto.firstName());
        person.setLastName(dto.lastName());
        person.setPassword(encryptedPassword);

        // SALVANDO O USUARIO NO BANCO DE DADOS
        person = personRepository.save(person);

		return person;
	}

    public PersonData createPersonData(PersonDataCreateDTO dto)
    {
        Optional<Person> existsUser = personRepository.findById(dto.personId());
        if (!existsUser.isPresent()) {
            throw Responses.badRequest("Usuario inexistente!");
        }

        PersonData data = new PersonData();
        data.setEmail("email@gmail.com");
        data.setTel("71 9 9999-9999");

        personDataRepository.save(data);

        existsUser.get().setData(data);

        personRepository.saveAndFlush(existsUser.get());

        return data;
    }

    public Person getPersonById(Long id) throws NotFoundException
    {
        Optional<Person> findPerson = personRepository.findById(id);

        if (!findPerson.isPresent()) {
            throw new NotFoundException();
        }

        return findPerson.get();
    }
}
