package com.eduardo.cursomc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.eduardo.cursomc.domain.Cliente;
import com.eduardo.cursomc.services.validation.ClienteInsertAnnotation;

@ClienteInsertAnnotation
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "O nome do cliente e obrigatorio")
	@Length(min = 5, max = 80, message = "O nome deve possuir entre 5 e 80 caracteres")
	private String nome;

	@NotEmpty(message = "Campo obrigatorio")
	@Email(message = "Email invalido")
	private String email;

	@NotEmpty(message = "O CPF/CNPJ e obrigatorio")
	private String cpfOuCnpj;

	private Integer tipo;
	
	@NotEmpty(message = "A senha e obrigatoria")
	private String senha;

	@NotEmpty(message = "O cliente deve possuir ao menos um endereco cadastrado")
	private List<EnderecoDTO> enderecos = new ArrayList<>();

	@NotEmpty(message = "O cliente deve possuir ao menos um telefone cadastrado")
	private Set<String> telefones = new HashSet<>();
	
	@NotEmpty(message = "O cliente deve possuir ao menos um perfil")
	private Set<Integer> perfis = new HashSet<>();

	public ClienteDTO() {
	}

	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
		this.cpfOuCnpj = cliente.getCpfOuCnpj();
		this.tipo = cliente.getTipo().getCodigo();
		this.senha = cliente.getSenha();
		this.telefones = cliente.getTelefones();
		this.perfis = cliente.getPerfis().stream().map(p -> p.getCodigo()).collect(Collectors.toSet());
		setEnderecos(cliente.getEnderecos().stream().map(e -> new EnderecoDTO(e)).collect(Collectors.toList()));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<EnderecoDTO> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<EnderecoDTO> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	public Set<Integer> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Integer> perfis) {
		this.perfis = perfis;
	}
}
