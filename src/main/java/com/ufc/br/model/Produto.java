package com.ufc.br.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy=false)
public class Produto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private double preco;
	
	@ManyToMany(mappedBy = "produtos")
	private List<Pessoa> pessoas;
	
	public Produto() { }
	
	public Produto(String nome, double preco) {
		this.nome = nome;
		this.preco = preco;
		this.pessoas = new ArrayList<>();
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	public boolean addPessoa(Pessoa pessoa) {
		this.pessoas.add(pessoa);
		return pessoa.getProdutos().add(this);
	}
	
	public boolean removerPessoa(Pessoa pessoa) {
		this.pessoas.remove(pessoa);
		return pessoa.getProdutos().remove(this);
	}
	
}
