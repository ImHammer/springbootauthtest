package com.hammerdev.authtest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.hammerdev.authtest.data.person.PersonCreateRequestDTO;
import com.hammerdev.authtest.utils.Utils;
import com.hammerdev.authtest.utils.validations.ValidationField;
import com.hammerdev.authtest.utils.validations.ValidationObject;
import com.hammerdev.authtest.utils.validations.ValidationType;

@SpringBootTest
public class ValidationTests
{	
	@Test
	void fieldValidation()
	{
		ValidationField validation = ValidationField.string().min(6, null).max(10, null).validate("Danilo");
		assertFalse(validation.canError(ValidationType.MIN), "Contem error de minimo");
		assertFalse(validation.canError(ValidationType.MAX), "Contem error de máximo");
	}

	@Test
	void objectValidation()
	{
		PersonCreateRequestDTO dto = new PersonCreateRequestDTO("ImHammer", "Danilo", "Jesus", "slakipoiew");

		ValidationObject validation = new ValidationObject();
		validation.string("username").minClear(6, "${FIELD_NAME} contem erro no minimo").max(16, "Username Contem erro no máximo").nonBlank("Username é vazio").nonNull("Username é nulo");
		validation.string("firstName").min(3, "${FIELD_NAME} min error").max(12, "FirstName max error").nonBlank("FirstName is blank").nonNull("FirstName is null");
		validation.reference("lastName", "firstName");
		validation.string("password").pattern("^[\\w@#$!]+$", "password contem caracteres incomun");
		
		assertDoesNotThrow(() -> validation.validateException(dto), "disparou uma exceção");
	}

	@Test
	void testando()
	{
		String pattern = "^[\\w@#$!]+$";
		assertTrue(Utils.match(pattern, "ljaofijsdkf"), "slapow");
	}

	@Test
	void testandoRecordFields()
	{
		PersonCreateRequestDTO dto = new PersonCreateRequestDTO("ImHammer", "Danilo", "Jesus", "slakipoiew");

		Field field = assertDoesNotThrow(() -> dto.getClass().getDeclaredField("username"), "Não obteu o campo username");
		assertDoesNotThrow(() -> field.setAccessible(true), "Não foi posssivel deixar acessível");
		String value = assertDoesNotThrow(() -> ((String) field.get(dto)), "Não conseguiu obter o valor do campo");
		assertEquals("ImHammer", value);
	}
}
