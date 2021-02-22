package com.eduardo.cursomc.domain.enums;

public enum TipoCliente {

	PESSOA_FISICA(1, "Pessoa Fisica"),
	PESSOA_JURIDICA(2, "Pessoa Juiridica");

	private int codigo;
	private String descricao;
	
	private TipoCliente(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (TipoCliente x : TipoCliente.values()) { 
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Tipo de Cliente invalido:" + codigo);
	}
}
