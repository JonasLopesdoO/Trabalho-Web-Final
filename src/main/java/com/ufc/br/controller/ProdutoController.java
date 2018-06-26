package com.ufc.br.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Produto;
import com.ufc.br.model.Produto;
import com.ufc.br.model.Produto;
import com.ufc.br.model.Produto;
import com.ufc.br.service.ProdutoService;


@Controller
@RequestMapping(path="/produto")
public class ProdutoController {
	
	@Autowired
	ProdutoService produtoService;

	@RequestMapping("/formulario")
	public ModelAndView formularioProduto() {
		ModelAndView mv = new ModelAndView("produto-formulario");
		mv.addObject("produto", new Produto());
		return mv;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvarProduto(Produto produto,@RequestParam(value= "imagem") MultipartFile imagem) {
		produtoService.adicionarProduto(produto, imagem);
		ModelAndView mv = new ModelAndView("redirect:/galeria");
		
		return mv;
	}
	
	@GetMapping("/listar")
	public ModelAndView listarProduto() {
		List<Produto> produtos = produtoService.getAllProdutos();
		ModelAndView mv = new ModelAndView("galeria");
		
		mv.addObject("todosProdutos", produtos);
		
		return mv;
	}
	
	@RequestMapping("/atualizar/{id}")
	public ModelAndView atualizarProduto(@PathVariable Long id) {
		Produto produto = produtoService.buscarPorId(id);
		ModelAndView mv = new ModelAndView("produto-formulario");
		mv.addObject("produto", produto);
		
		return mv;
	}
	
	@RequestMapping("/excluir/{id}")
	public ModelAndView excluirProduto(@PathVariable Long id) {
		produtoService.removerProduto(id);
		ModelAndView mv = new ModelAndView("redirect:/galeria");
		return mv;
	}
	
	private List<Produto> getAllProdutos() {
		return produtoService.getAllProdutos();
	}

	public boolean existsByIdProduto(Long idProduto) {
		return produtoService.existsById(idProduto);
	}

	public Produto buscaPorId(Long idProduto) {
		return produtoService.buscarPorId(idProduto);
	}
	
}
