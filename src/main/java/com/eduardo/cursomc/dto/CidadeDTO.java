package com.eduardo.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.eduardo.cursomc.domain.Cidade;

public class CidadeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message="O nome e obrigatorio")
	@Length(min=5, max=80, message="O nome deve possuir entre 5 e 80 caracteres")
	private String nome;

	private EstadoDTO estado;

	public CidadeDTO() {
	}

	public CidadeDTO(Integer id, String nome, EstadoDTO estado) {
		super();
		this.id = id;
		this.nome = nome;
		this.estado = estado;
	}
	
	public CidadeDTO(Cidade cidade) {
		super();
		this.id = cidade.getId();
		this.nome = cidade.getNome();
		this.estado = new EstadoDTO(cidade.getEstado());
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

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}
}
