package com.hammerdev.authtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hammerdev.authtest.config.ApplicationConfig;

@SpringBootTest
class AuthtestApplicationTests
{
	@Autowired
	ApplicationConfig config;

	@Test
	void testandoConfig()
	{
		assertEquals("joaoemaria", config.getJwt().getSecret(), "SLAPOW O QUE É ESSA MSG");	
	}
}
