package com.ufc.br.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.annotations.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Carrinho;
import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Produto;
import com.ufc.br.service.PessoaService;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private ProdutoController produtoController;
	
	public Carrinho carrinho() {
		return Carrinho.getInstance();
	}

	@RequestMapping("/formulario")
	public ModelAndView formularioPessoa() {
		ModelAndView mv = new ModelAndView("pessoa-formulario");
		mv.addObject("pessoa", new Pessoa());
		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView salvarPessoa(Pessoa pessoa) {
		
		pessoaService.adicionarPessoa(pessoa);
				
		ModelAndView mv = new ModelAndView("redirect:/pessoa/logar");

		return mv;
	}

	@GetMapping("/listar")
	public ModelAndView listarPessoa() {
		List<Pessoa> pessoas = pessoaService.retornarTodasAsPessoas();
		ModelAndView mv = new ModelAndView("pagina-listagem");

		mv.addObject("todasAsPessoas", pessoas);

		return mv;
	}

	@RequestMapping("/atualizar/{id}")
	public ModelAndView atualizarPessoa(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.buscarPorId(id);
		ModelAndView mv = new ModelAndView("pessoa-formulario");
		mv.addObject("pessoa", pessoa);

		return mv;
	}

	@RequestMapping("/excluir/{id}")
	public ModelAndView excluirPessoa(@PathVariable Long id) {
		pessoaService.removerPessoa(id);
		ModelAndView mv = new ModelAndView("redirect:/pessoa/listar");
		return mv;
	}

	@RequestMapping("/logar")
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}

	@RequestMapping("/adicionarAoCarrinho/{id}")
	public ModelAndView adicionarAoCarrinho(@PathVariable Long id) {
		ModelAndView model = new ModelAndView("redirect:/galeria/");
		model.addObject(new Produto());
			
		if(existsByIdProduto(id)) {
			Produto produto = produtoController.buscaPorId(id);
			carrinho().adicionarProdutoAoCarrinho(produto);
		}
		
	return model;
	}

	@RequestMapping("/removerDoCarrinho/{id}")
	public ModelAndView removerDoCarrinho(@PathVariable Long id) {
		
		ModelAndView model = new ModelAndView("redirect:/carrinho");
		
		if(existsByIdProduto(id)) {
			Produto produto = produtoController.buscaPorId(id);
			carrinho().removerProdutoDoCarrinho(produto);
		}
		
		return model;
	}
	
	@RequestMapping("/adicionarAoHistorico")
	public ModelAndView adicionarAoHistorico() {
		
		ModelAndView model = new ModelAndView("redirect:/historico");
		
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
				
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		
		List<Produto> carrinho = carrinho().listarProdutos(); //pega os produtos do carrinho e coloca em uma lista de produtos
		pessoa.getProdutos().addAll(carrinho);//adicionar todos os produtos do carrinho ao histórico
		carrinho().esvaziar(); //esvazia o carrinho
		
		pessoaService.adicionarPessoaSimplificado(pessoa);
		
		model.addObject("historico",pessoa.getProdutos());
		
		return model;
	}
	
	private boolean existsByIdProduto(Long id) {
		if(produtoController.existsByIdProduto(id)) {
			return true;
		}
		return false;
	}

}
