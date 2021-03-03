package com.eduardo.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.eduardo.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "O nome e obrigatorio")
	@Length(min = 5, max = 80, message = "O nome deve possuir entre 5 e 80 caracteres")
	private String nome;

	private Double preco;

	public ProdutoDTO() {
	}

	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.preco = produto.getPreco();
	}

	public ProdutoDTO(Integer id, String nome, Double preco) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
