package com.eduardo.cursomc.dto;

import java.io.Serializable;

import com.eduardo.cursomc.domain.enums.Estado;

public class EstadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;

	private String nome;

	public EstadoDTO() {
	}

	public EstadoDTO(Integer codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.nome = descricao;
	}

	public EstadoDTO(Estado estado) {
		super();
		this.codigo = estado.getCodigo();
		this.nome = estado.getNome();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}
}
