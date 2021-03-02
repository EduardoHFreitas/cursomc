package com.eduardo.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class ClienteUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "O nome do cliente e obrigatorio")
	@Length(min = 5, max = 80, message = "O nome deve possuir entre 5 e 80 caracteres")
	private String nome;

	@NotEmpty(message = "Campo obrigatorio")
	@Email(message = "Email invalido")
	private String email;

	public ClienteUpdateDTO() {
	}

	public ClienteUpdateDTO(String nome, String email) {
		this.nome = nome;
		this.email = email;
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
}
