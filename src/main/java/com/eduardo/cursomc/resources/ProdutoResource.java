package com.eduardo.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.cursomc.domain.Produto;
import com.eduardo.cursomc.dto.ProdutoDTO;
import com.eduardo.cursomc.resources.util.URLUtils;
import com.eduardo.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProdutoDTO>> findAll() {
		List<Produto> produtos = produtoService.findAll();
		
		List<ProdutoDTO> produtosDTO = produtos.stream().map(c -> new ProdutoDTO(c)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(produtosDTO);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> findById(@PathVariable Integer id) {
		Produto produto = produtoService.findById(id);
		
		return ResponseEntity.ok().body(produto);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> search(@RequestParam(value="nome", defaultValue="") String nome,
												   @RequestParam(value="idsCategorias", defaultValue="") String idsCategorias,
												   @RequestParam(value="page", defaultValue="0") Integer page, 
												   @RequestParam(value="size", defaultValue="24") Integer size, 
												   @RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
												   @RequestParam(value="direction", defaultValue="ASC") String direction)  {
		
		String nomeDecoded = URLUtils.decodeStringParam(nome);
		
		List<Integer> categorias = URLUtils.decodeIntegerList(idsCategorias);
		
		Page<Produto> findPaged = produtoService.search(nomeDecoded, categorias, page, size, orderBy, direction);
		
		Page<ProdutoDTO> produtosDTO = findPaged.map(c -> new ProdutoDTO(c));
		
		return ResponseEntity.ok().body(produtosDTO);
	}

}
