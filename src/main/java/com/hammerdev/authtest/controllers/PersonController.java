package com.hammerdev.authtest.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.hammerdev.authtest.data.person.PersonCreateRequestDTO;
import com.hammerdev.authtest.data.person.PersonResponseDTO;
import com.hammerdev.authtest.entities.Person;
import com.hammerdev.authtest.services.person.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonController
{
    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody PersonCreateRequestDTO body) throws Exception
    {
        Person person = personService.createPerson(body);
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:4000")
            .path("persons")
            .path("/" + String.valueOf(person.getId()))
            .build()
            .toUri();

        return ResponseEntity.created(uri).body(person);
    }

    @GetMapping("/{_id}")
    public ResponseEntity<PersonResponseDTO> read(@PathVariable String _id)
    {
        try {
            Long id = Long.valueOf(_id);
            Person person = personService.getPersonById(id);
            return ResponseEntity.ok().body(new PersonResponseDTO(person));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passe um id valido!");
        }
    }
}
