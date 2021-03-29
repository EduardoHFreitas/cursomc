package com.eduardo.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.eduardo.cursomc.domain.Cidade;
import com.eduardo.cursomc.domain.enums.Estado;
import com.eduardo.cursomc.dto.CidadeDTO;
import com.eduardo.cursomc.repositories.CidadeRepository;
import com.eduardo.cursomc.services.exceptions.DataIntegrityException;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> findAll() {
		return cidadeRepository.findAll();
	}
	
	public Page<Cidade> findPaged(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		
		return cidadeRepository.findAll(pageRequest);
	}

	public Cidade findById(Integer id) {
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		return cidade.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
	}

	public Cidade insert(Cidade cidade) {
		cidade.setId(null);
		return cidadeRepository.save(cidade);
	}

	public Cidade update(Cidade cidade) {
		Cidade cidadeSave = findById(cidade.getId());
		updateData(cidadeSave, cidade);
		return cidadeRepository.save(cidadeSave);
	}

	public void delete(Integer id) {
		Cidade cidade = findById(id);

		try {
			cidadeRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
					"A cidade " + cidade.getNome() + " nao pode ser excluida pois possui produtos vinculados");
		}
	}
	
	public Cidade fromDTO(CidadeDTO dto) {
		Estado estado = Estado.toEnum(dto.getEstado().getCodigo());
		return new Cidade(dto.getId(), dto.getNome(), estado);
	}
	
	private void updateData(Cidade cidadeSave, Cidade cidade) {
		if (!cidade.getNome().isEmpty()) {
			cidadeSave.setNome(cidade.getNome());
		}
	}
}
