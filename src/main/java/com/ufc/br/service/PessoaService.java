package com.ufc.br.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Role;
import com.ufc.br.repository.PessoaRepository;
import com.ufc.br.repository.RoleRepository;
import com.ufc.br.util.AulaFileUtils;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	public void adicionarPessoa(Pessoa pessoa) {
		pessoa.setSenha(new BCryptPasswordEncoder().encode(pessoa.getSenha()));
		Role role = new Role();
		role.setPapel("ROLE_USER");
				
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		pessoas.add(pessoa);
		
		pessoa.setRoles(roles);
		role.setPessoas(pessoas);
		
		roleRepository.save(role);
		pessoaRepository.save(pessoa);
	}
	
	public void adicionarPessoaSimplificado(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
	}


	public List<Pessoa> retornarTodasAsPessoas() {
		return pessoaRepository.findAll();
	}
	
	public Pessoa buscarPorId(Long id) {
		return pessoaRepository.getOne(id);
	}


	public void removerPessoa(Long id) {
		pessoaRepository.deleteById(id);
		
	}


	public Pessoa buscaPorLogin(String username) {
		return pessoaRepository.findByLogin(username);
	}
	

}
