package com.ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ufc.br.model.Produto;
import com.ufc.br.model.Produto;
import com.ufc.br.model.Produto;
import com.ufc.br.repository.ProdutoRepository;
import com.ufc.br.util.AulaFileUtils;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;

	public void adicionarProduto(Produto produto, MultipartFile imagem) {
		
		String caminho = "src/main/resources/static/img/" + produto.getNome() + ".png";
		AulaFileUtils.salvarImagem(caminho,imagem);
		
		produtoRepository.save(produto);
	}
	
	public List<Produto> getAllProdutos() {
		return produtoRepository.findAll();
	}
	
	
	public Produto buscarPorId(Long id) {
		return produtoRepository.getOne(id);
	}

	public void removerProduto(Long id) {
		produtoRepository.deleteById(id);
		
	}

	public boolean existsById(Long idProduto) {
		return produtoRepository.existsById(idProduto);
	}
}
