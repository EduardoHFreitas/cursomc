package com.eduardo.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.dto.ClienteDTO;
import com.eduardo.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> clientes = clienteService.findAll();
		
		List<ClienteDTO> clientesDTO = clientes.stream().map(c -> new ClienteDTO(c)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(clientesDTO);
	}
	
	@RequestMapping(value = "/paged", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPaged(@RequestParam(value="page", defaultValue="0") Integer page, 
														@RequestParam(value="size", defaultValue="24") Integer size, 
														@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
														@RequestParam(value="direction", defaultValue="ASC") String direction)  {
		
		Page<Cliente> findPaged = clienteService.findPaged(page, size, orderBy, direction);
		
		Page<ClienteDTO> clientesDTO = findPaged.map(c -> new ClienteDTO(c));
		
		return ResponseEntity.ok().body(clientesDTO);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> findById(@PathVariable Integer id) {
		Cliente cliente = clienteService.findById(id);
		
		return ResponseEntity.ok().body(cliente);
	}
	

//	@RequestMapping(method = RequestMethod.POST)
//	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO clienteDTO) {
//		Cliente cliente = clienteService.fromDTO(clienteDTO); 
//		cliente = clienteService.insert(cliente);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteDTO.getId())
//				.toUri();
//		return ResponseEntity.created(uri).build();
//	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO clienteDTO) {
		Cliente cliente = clienteService.fromDTO(clienteDTO);
		
		cliente.setId(id);
		cliente = clienteService.update(cliente);

		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		clienteService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
