package com.eduardo.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.eduardo.cursomc.domain.Categoria;
import com.eduardo.cursomc.repositories.CategoriaRepository;
import com.eduardo.cursomc.services.exceptions.DataIntegrityException;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	public Categoria findById(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		findById(categoria.getId());
		return categoriaRepository.save(categoria);
	}

	public void delete(Integer id) {
		Categoria categoria = findById(id);

		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
					"A categoria " + categoria.getNome() + " nao pode ser excluida pois possui produtos vinculados");
		}
	}
}
