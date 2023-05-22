package com.hammerdev.authtest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Primary
@Configuration
@ConfigurationProperties(prefix = "mapplication")
public class ApplicationConfig
{
	private int saltRounds = 10;

	private JwtConfig jwt;

	public int getSaltRounds()
	{
		return saltRounds;
	}

	public void setSaltRounds(int saltRounds)
	{
		this.saltRounds = saltRounds;
	}

	public JwtConfig getJwt()
	{
		return jwt;
	}

	public void setJwt(JwtConfig jwt)
	{
		this.jwt = jwt;
	}	
}
