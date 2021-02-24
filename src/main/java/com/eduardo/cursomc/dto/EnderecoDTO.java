package com.eduardo.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class EnderecoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "O logradouro e obrigatorio")
	private String logradouro;

	@NotEmpty(message = "O numero e obrigatorio")
	private String numero;

	private String complemento;

	@NotEmpty(message = "O bairro e obrigatorio")
	private String bairro;

	@NotEmpty(message = "O CEP e obrigatorio")
	private String cep;

	@NotEmpty(message = "A cidade e obrigatoria")
	private Integer cidadeId;
	

	public EnderecoDTO() {
	}

	public EnderecoDTO(String logradouro, String numero, String complemento, String bairro, String cep, Integer cidadeId) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.cidadeId = cidadeId;
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

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}
}
