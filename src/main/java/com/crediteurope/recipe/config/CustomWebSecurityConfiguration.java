package com.crediteurope.recipe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${spring.security.enabled:true}")
	private Boolean enabled;

	@Value("${spring.security.user.name}")
	private String userName;

	@Value("${spring.security.user.password}")
	private String userPassword;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (enabled)
			http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
		else
			http.csrf().disable().authorizeRequests().anyRequest().anonymous().and().httpBasic().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser(userName)
				.password(passwordEncoder().encode(userPassword)).authorities("ROLE_USER");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
