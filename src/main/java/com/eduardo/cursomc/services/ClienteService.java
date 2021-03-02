package com.eduardo.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.domain.Endereco;
import com.eduardo.cursomc.domain.enums.TipoCliente;
import com.eduardo.cursomc.dto.ClienteDTO;
import com.eduardo.cursomc.dto.ClienteUpdateDTO;
import com.eduardo.cursomc.repositories.ClienteRepository;
import com.eduardo.cursomc.repositories.EnderecoRepository;
import com.eduardo.cursomc.services.exceptions.DataIntegrityException;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> findPaged(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);

		return clienteRepository.findAll(pageRequest);
	}

	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		
		Cliente clienteSalvo = clienteRepository.save(cliente);
		
		cliente.setEnderecos(cliente.getEnderecos().stream().map(e -> e.setCliente(clienteSalvo)).collect(Collectors.toList()));
		enderecoRepository.saveAll(cliente.getEnderecos());
		
		return clienteSalvo;
	}

	public Cliente update(Cliente cliente) {
		Cliente clienteSave = findById(cliente.getId());
		updateData(clienteSave, cliente);
		return clienteRepository.save(clienteSave);
	}

	public void delete(Integer id) {
		Cliente cliente = findById(id);

		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
					"O cliente " + cliente.getNome() + " nao pode ser excluido pois possui pedido(s) vinculado(s)");
		}
	}

	public Cliente fromDTO(ClienteDTO dto) {
		Cliente cliente = new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(), TipoCliente.toEnum(dto.getTipo()));
		
		if (!dto.getEnderecos().isEmpty()) {
			List<Endereco> enderecos = dto.getEnderecos().stream().map(e -> new Endereco(e)).collect(Collectors.toList());

			cliente.getEnderecos().addAll(enderecos);
		}
		
		if (!dto.getTelefones().isEmpty()) {
			cliente.getTelefones().addAll(dto.getTelefones());
		}
		
		return cliente;
	}
	
	public Cliente fromDTO(ClienteUpdateDTO dto) {
		return new Cliente(null, dto.getNome(), dto.getEmail(), null, null);
	}

	private void updateData(Cliente clienteSave, Cliente cliente) {
		if (!cliente.getEmail().isEmpty()) {
			clienteSave.setEmail(cliente.getEmail());
		}
		
		if (!cliente.getNome().isEmpty()) {
			clienteSave.setNome(cliente.getNome());
		}
	}
}
