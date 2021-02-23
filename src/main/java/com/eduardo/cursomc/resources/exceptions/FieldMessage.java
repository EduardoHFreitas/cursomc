package com.eduardo.cursomc.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String erro;
	private String message;

	public FieldMessage() {
	}

	public FieldMessage(String erro, String message) {
		super();
		this.erro = erro;
		this.message = message;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
