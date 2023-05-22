package com.hammerdev.authtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.hammerdev.authtest.config.ApplicationConfig;
import com.hammerdev.authtest.config.JwtConfig;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class AuthtestApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(AuthtestApplication.class, args);
	}

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.csrf().disable();
        return http.build();
    }

    @Bean
    public JwtConfig getJWTConfig()
    {
        return new JwtConfig();
    }
}
