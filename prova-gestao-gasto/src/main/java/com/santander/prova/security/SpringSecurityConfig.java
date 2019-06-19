package com.santander.prova.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
    protected void configure(AuthenticationManagerBuilder autenticacao) throws Exception {

        autenticacao.inMemoryAuthentication()
        		.withUser("adminTeste").password(encoder().encode("1234")).roles("ADMIN")
        		.and()
                .withUser("usuarioTeste").password(encoder().encode("1234")).roles("USER");

    }

	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/gasto/**").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/gasto/*").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/gasto/**").hasRole("USER")
            .antMatchers(HttpMethod.GET, "/categoria/**").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/categoria/*").hasRole("USER")
            .and()
            .csrf().disable()
            .formLogin().disable();
    }
}
