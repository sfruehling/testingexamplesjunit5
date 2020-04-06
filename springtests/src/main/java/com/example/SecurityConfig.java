package com.example;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@Value("${de.andrena.springbootadvancedtesting.username}")
	private String username;

	@Value("${de.andrena.springbootadvancedtesting.password}")
	private String password;

	@Autowired
	private Environment environment;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (Arrays.asList(environment.getActiveProfiles()).contains("local")) {
			logger.error("Running locally without security checks");
			http.authorizeRequests().anyRequest().permitAll();
			return;
		}

		http //
				.authorizeRequests().anyRequest().authenticated() //
				.and().httpBasic();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth //
				.inMemoryAuthentication() //
				.withUser(username) //
				.password("{noop}" + password) //
				.roles("USER");
	}
}
