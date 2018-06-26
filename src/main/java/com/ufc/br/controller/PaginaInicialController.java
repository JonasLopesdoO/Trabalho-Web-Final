package com.ufc.br.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Carrinho;
import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Produto;
import com.ufc.br.service.PessoaService;

@Controller
@RequestMapping(path= "/")
public class PaginaInicialController {
	@Autowired
	ProdutoController produtoController;
	@Autowired
	PessoaService pessoaService;
	
	@RequestMapping("/")
	public ModelAndView paginaInicial() {
		ModelAndView model = new ModelAndView("index");
		
		List<Produto> todosProdutos = (List<Produto>) produtoController.listarProduto().getModel().get("todosProdutos");
		List<Produto> produtosIniciais = todosProdutos.subList(0, 5);
		
		model.addObject("produtosHome", produtosIniciais);
		
		return model;
	}
	
	//pagina estática
	@RequestMapping("/sobre")
	public ModelAndView paginaSobre() {
		ModelAndView model = new ModelAndView("sobre");
		return model;
	}
	
	@RequestMapping("/galeria")
	public ModelAndView paginaGeleria() {
		ModelAndView model = new ModelAndView("galeria");
		
		List<Produto> todosProdutos = (List<Produto>) produtoController.listarProduto().getModel().get("todosProdutos");
		
		model.addObject("todosProdutos", todosProdutos);
		return model;
	}
	
	//pagina estática
	@RequestMapping("/contato")
	public ModelAndView paginaContato() {
		ModelAndView model = new ModelAndView("contato");
		return model;
	}
	
	@RequestMapping("/carrinho")
	public ModelAndView paginaCarrinho() {
		List<Produto> carrinho = Carrinho.getInstance().listarProdutos();
		ModelAndView model = new ModelAndView("carrinho");
		model.addObject("carrinhoProduto", carrinho);
		model.addObject("total", Carrinho.getInstance().valorTotal());
		
		return model;
	}
	
	@RequestMapping("/historico")
	public ModelAndView paginaHistorico() {
		ModelAndView model = new ModelAndView("historico");
		
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
				
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		
		model.addObject("historico",pessoa.getProdutos());
		
		return model;
	}
	
	@RequestMapping("/gerencia")
	public ModelAndView paginaGerencia() {
		ModelAndView model = new ModelAndView("gerencia");
		
		return model;
	}
}
