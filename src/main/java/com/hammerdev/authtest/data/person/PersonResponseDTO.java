package com.hammerdev.authtest.data.person;

import com.hammerdev.authtest.entities.Person;
import com.hammerdev.authtest.entities.PersonData;

public record PersonResponseDTO(
    String firstName,
    String lastName,
    PersonDataResponseDTO data
)
{
    public PersonResponseDTO(Person person)
    {
        this(person.getFirstName(), person.getLastName(), new PersonDataResponseDTO(person.getData()));
    }

    public static record PersonDataResponseDTO(String email, String tel)
    {
        public PersonDataResponseDTO(PersonData personData)
        {
            this(personData.getEmail(), personData.getTel());
        }
    }
}
