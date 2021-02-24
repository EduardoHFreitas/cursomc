package com.eduardo.cursomc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.eduardo.cursomc.domain.Cliente;

public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "O nome do cliente e obrigatorio")
	@Length(min = 5, max = 80, message = "O nome deve possuir entre 5 e 80 caracteres")
	private String nome;

	@NotEmpty(message = "Campo obrigatorio")
	@Email(message = "Email invalido")
	private String email;

	@NotEmpty(message = "o CPF/CNPJ e obrigatorio")
	private String cpfOuCnpj;

	private Integer tipo;

	@NotEmpty(message = "O cliente deve possuir ao menos um endereco cadastrado")
	private List<EnderecoDTO> enderecos = new ArrayList<>();
	
	@NotEmpty(message = "O cliente deve possuir ao menos um telefone cadastrado")
	private List<String> telefones = new ArrayList<>();

	public ClienteDTO() {
	}
	
	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
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

	public List<EnderecoDTO> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoDTO> enderecos) {
		this.enderecos = enderecos;
	}

	public List<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}
}
