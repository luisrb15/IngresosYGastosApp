package com.ingresosygastos.configuraciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ingresosygastos.services.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadConfiguracion extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("usuarioService")
	public UsuarioService usuarioService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/admin/*").hasRole("ADMIN")
				.antMatchers("/css/*", "/js/*", "/img/*", "/**")
				.permitAll()
				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/logincheck")
				.usernameParameter("email")
				.passwordParameter("clave")
				.defaultSuccessUrl("/")
				.permitAll()
				.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.permitAll()
				.and().csrf().disable();
	}
}