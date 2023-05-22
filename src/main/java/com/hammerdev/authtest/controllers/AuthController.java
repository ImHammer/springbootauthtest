package com.hammerdev.authtest.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hammerdev.authtest.data.auth.UserAuthRequestDTO;
import com.hammerdev.authtest.entities.Person;
import com.hammerdev.authtest.repositories.PersonRepository;
import com.hammerdev.authtest.services.AuthService;
import com.hammerdev.authtest.utils.Responses;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    PersonRepository userRepository;

    @Autowired
    AuthService authService;

    @PostMapping
    public ResponseEntity<Object> authenticate(@RequestHeader Map<String, String> headers, @RequestBody UserAuthRequestDTO body) throws Throwable
    {
        if (body.username() == null || body.password() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect fields!");
        }

        Optional<Person> findUser = userRepository.findByUsername(body.username());

        // USANDO AQUELA RFC LÁ
        if (!findUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username or password are incorrect!");
        }

        // VALIDANDO A SENHA
        if (!BCrypt.checkpw(body.password(), findUser.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username or password are incorrect!");
        }

        // CRIANDO O TOKEN
        try {
            return Responses.ok(authService.createToken(findUser.get()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getCause());
        }
    }
}
