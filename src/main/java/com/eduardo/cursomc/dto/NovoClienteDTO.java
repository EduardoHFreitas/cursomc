package com.eduardo.cursomc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class NovoClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "O nome do cliente e obrigatorio")
	@Length(min = 5, max = 80, message = "O nome deve possuir entre 5 e 80 caracteres")
	private String nome;

	@NotEmpty(message = "Campo obrigatorio")
	@Email(message = "Email invalido")
	private String email;

	@NotEmpty(message = "o CPF/CNPJ e obrigatorio")
	private String cpfOuCnpj;

	private Integer tipo;

	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;

	@NotEmpty(message = "O cliente deve possuir ao menos um telefone cadastrado")
	private List<String> telefones = new ArrayList<>();

	private Integer cidadeId;

	public NovoClienteDTO() {
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

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public List<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}
}
