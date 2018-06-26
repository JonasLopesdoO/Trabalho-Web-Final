package com.ufc.br.model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
	
	private static Carrinho carrinho;
		
	private List<Produto> produtos;
	
	private Carrinho() {
		produtos = new ArrayList<Produto>();
	}
	
	public static Carrinho getInstance() {
		if(carrinho == null) {
			carrinho = new Carrinho();
		}
		return carrinho;
	}
	
	public void adicionarProdutoAoCarrinho(Produto produto) {
		produtos.add(produto);
	}
	
	public void removerProdutoDoCarrinho(Produto produto) {
		for (int i = 0; i < produtos.size(); i++) {
			if(produtos.get(i).getId() == produto.getId()) {
				produtos.remove(i);
				break;
			}
		}
	}
	
	public float valorTotal() {
		float total = 0;
		
		for (Produto produto : produtos) {
			total += produto.getPreco();
		}
		return total;
	}
	
	public List<Produto> listarProdutos() {
		return produtos;
	}

	public void esvaziar() {
		produtos.clear();
	}
	
}
