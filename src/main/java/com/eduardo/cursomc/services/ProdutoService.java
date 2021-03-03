package com.eduardo.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.eduardo.cursomc.domain.Categoria;
import com.eduardo.cursomc.domain.Produto;
import com.eduardo.cursomc.repositories.CategoriaRepository;
import com.eduardo.cursomc.repositories.ProdutoRepository;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}
	
	public Produto findById(Integer id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		return produto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));	
	}
	

	public Page<Produto> search(String nome, List<Integer> idsCategorias, Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);

		List<Categoria> categorias = categoriaRepository.findAllById(idsCategorias);

		if (idsCategorias.isEmpty()) {
			return produtoRepository.findDistinctByNomeContaining(nome, pageRequest);
		} else {
			return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		}
	}
}
