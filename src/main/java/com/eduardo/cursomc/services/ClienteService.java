package com.eduardo.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.domain.Endereco;
import com.eduardo.cursomc.domain.enums.Perfil;
import com.eduardo.cursomc.domain.enums.TipoCliente;
import com.eduardo.cursomc.dto.ClienteDTO;
import com.eduardo.cursomc.dto.ClienteUpdateDTO;
import com.eduardo.cursomc.repositories.ClienteRepository;
import com.eduardo.cursomc.repositories.EnderecoRepository;
import com.eduardo.cursomc.security.User;
import com.eduardo.cursomc.services.exceptions.AuthorizationException;
import com.eduardo.cursomc.services.exceptions.DataIntegrityException;
import com.eduardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> findPaged(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);

		return clienteRepository.findAll(pageRequest);
	}

	public Cliente findById(Integer id) {
		User user = UserService.getAuthenticatedUser();

		if (user == null || (!user.hasHole(Perfil.ADMIN) && !id.equals(user.getId()))) {
			throw new AuthorizationException("Acesso negado!");
		}

		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
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
			throw new DataIntegrityException("O cliente " + cliente.getNome() + " nao pode ser excluido pois possui pedido(s) vinculado(s)");
		}
	}

	public Cliente findByEmail(String email) {
		User user = UserService.getAuthenticatedUser();

		if (user == null || (!user.hasHole(Perfil.ADMIN) && !email.equals(user.getUsername()))) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente == null) {
			throw new ObjectNotFoundException("Cliente " + email + " não encontrado!");
		}
		
		return cliente;
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		User user = UserService.getAuthenticatedUser();

		if (user == null) {
			throw new AuthorizationException("Acesso negado!");
		}

		BufferedImage image = imageService.getJpgImageFromFile(multipartFile);
		image = imageService.cropSquare(image);
		image = imageService.resize(image, size);

		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(image, "jpg"), fileName, "image");
	}

	public Cliente fromDTO(ClienteDTO dto) {
		String senha = bCryptPasswordEncoder.encode(dto.getSenha());

		Cliente cliente = new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(), TipoCliente.toEnum(dto.getTipo()), senha);

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
		return new Cliente(null, dto.getNome(), dto.getEmail(), null, null, null);
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
