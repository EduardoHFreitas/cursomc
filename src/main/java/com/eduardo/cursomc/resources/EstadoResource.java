package com.eduardo.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.cursomc.domain.enums.Estado;
import com.eduardo.cursomc.dto.EstadoDTO;
import com.eduardo.cursomc.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> estados = estadoService.findAll();
		
		List<EstadoDTO> estadosDTO = estados.stream().map(c -> new EstadoDTO(c)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(estadosDTO);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Estado> findByCodigo(@PathVariable Integer codigo) {
		Estado estado = estadoService.findByCodigo(codigo);

		return ResponseEntity.ok().body(estado);
	}
}