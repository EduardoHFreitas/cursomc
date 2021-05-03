package com.eduardo.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.eduardo.cursomc.domain.Cidade;
import com.eduardo.cursomc.domain.Endereco;

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
	
	private CidadeDTO cidade;
	
	public EnderecoDTO() {
	}

	public EnderecoDTO(String logradouro, String numero, String complemento, String bairro, String cep, Integer cidadeId, Cidade cidade) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.cidadeId = cidadeId;
		this.cidade = new CidadeDTO(cidade);
	}
	
	public EnderecoDTO(Endereco endereco) {
		this.logradouro = endereco.getLogradouro();
		this.numero = endereco.getNumero();
		this.complemento = endereco.getComplemento();
		this.bairro = endereco.getBairro();
		this.cep = endereco.getCep();
		this.cidadeId = (endereco.getCidade() == null ? null : endereco.getCidade().getId());
		this.cidade = (endereco.getCidade() == null ? null : new CidadeDTO(endereco.getCidade()));
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

	public CidadeDTO getCidade() {
		return cidade;
	}

	public void setCidade(CidadeDTO cidade) {
		this.cidade = cidade;
	}
}
