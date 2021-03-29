package com.eduardo.cursomc.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eduardo.cursomc.domain.enums.Estado;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {

	public List<Estado> findAll() {
		return Arrays.asList(Estado.values());
	}
	
	public Estado findByCodigo(Integer codigo) {
		Optional<Estado> estado = Optional.of(Estado.toEnum(codigo));
		return estado.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + codigo + ", Tipo: " + Estado.class.getName()));
	}
}
