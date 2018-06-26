package com.ufc.br.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ufc.br.security.UserDetailsServiceImplementacao;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImplementacao userDetailsImplementacao;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		.antMatchers("/pessoa/formulario").permitAll()
		.antMatchers("/pessoa/salvar").permitAll() // Pessoa com papel "USER" ou "ADMIN" acessa /pessoa/salvar
		
		.antMatchers("/pessoa/listar}").hasRole("ADMIN")
		.antMatchers("/pessoa/atualizar/{id}").hasAnyRole("USER","ADMIN")
		.antMatchers("/pessoa/excluir/{id}").hasRole("ADMIN")
		.antMatchers("/pessoa/adicionarAoCarrinho/{id}").hasRole("USER")
		.antMatchers("/pessoa/removerDoCarrinho/{id}").hasRole("USER")
		.antMatchers("/pessoa/adicionarAoHistorico").hasRole("USER")
		
		.antMatchers("/produto/formulario").hasRole("ADMIN")
		.antMatchers("/produto/salvar").hasRole("ADMIN")
		.antMatchers("/produto/formulario").hasRole("ADMIN")
		.antMatchers("/produto/atualizar/{id}").hasRole("ADMIN")
		.antMatchers("/produto/excluir/{id}").hasRole("ADMIN")
		
		.antMatchers("/").permitAll() // /pessoa/listar todo mundo pode acessar
		.antMatchers("/sobre").permitAll()
		.antMatchers("/contato").permitAll()
		.antMatchers("/galeria").permitAll()
		.antMatchers("/carrinho").hasRole("USER")
		.antMatchers("/historico").hasRole("USER")
		
		
		.anyRequest().authenticated() // o resto precisa está autenticado
		
		.and()
		.formLogin()
		.loginPage("/pessoa/logar").permitAll() // Esse é o controller que chama nosso formulario
	
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.and()
		.logout()
		.logoutSuccessUrl("/pessoa/logar?logout") // logout sucesso
		.permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsImplementacao).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/css/**", "/js/**","/img/**"); // ignora e permite uri's com esses arquivos
	}

}
