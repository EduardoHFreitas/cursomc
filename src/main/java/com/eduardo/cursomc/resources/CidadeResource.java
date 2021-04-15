package com.eduardo.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eduardo.cursomc.domain.Cidade;
import com.eduardo.cursomc.dto.CidadeDTO;
import com.eduardo.cursomc.services.CidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeResource {

	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findAll() {
		List<Cidade> cidades = cidadeService.findAll();
		
		List<CidadeDTO> cidadesDTO = cidades.stream().map(c -> new CidadeDTO(c)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(cidadesDTO);
	}

	@RequestMapping(value = "/{estado_id}", method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findAllByEstado(@PathVariable Integer estado_id) {
		List<Cidade> cidades = cidadeService.findAllByEstado(estado_id);
		
		List<CidadeDTO> cidadesDTO = cidades.stream().map(c -> new CidadeDTO(c)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(cidadesDTO);
	}
	
	@RequestMapping(value = "/paged", method = RequestMethod.GET)
	public ResponseEntity<Page<CidadeDTO>> findPagedByEstado(@RequestParam(value="page", defaultValue="0") Integer page, 
												     		 @RequestParam(value="size", defaultValue="24") Integer size, 
												     		 @RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
												     		 @RequestParam(value="direction", defaultValue="ASC") String direction)  {
		
		Page<Cidade> findPaged = cidadeService.findPaged(page, size, orderBy, direction); 
		
		Page<CidadeDTO> cidadesDTO = findPaged.map(c -> new CidadeDTO(c));
		
		return ResponseEntity.ok().body(cidadesDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cidade> findById(@PathVariable Integer id) {
		Cidade cidade = cidadeService.findById(id);

		return ResponseEntity.ok().body(cidade);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CidadeDTO cidadeDTO) {
		Cidade cidade = cidadeService.fromDTO(cidadeDTO); 
		cidade = cidadeService.insert(cidade);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cidadeDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		cidadeService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}