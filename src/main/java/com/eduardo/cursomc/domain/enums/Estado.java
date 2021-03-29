package com.eduardo.cursomc.domain.enums;

public enum Estado {

	RO(11,"Rondônia"),
	AC(12,"Acre"),
	AM(13,"Amazonas"),
	RR(14,"Roraima"),
	PA(15,"Pará"),
	AP(16,"Amapá"),
	TO(17,"Tocantins"),
	MA(21,"Maranhão"),
	PI(22,"Piauí"),
	CE(23,"Ceará"),
	RN(24,"Rio Grande do Norte"),
	PB(25,"Paraíba"),
	PE(26,"Pernambuco"),
	AL(27,"Alagoas"),
	SE(28,"Sergipe"),
	BA(29,"Bahia"),
	MG(31,"Minas Gerais"),
	ES(32,"Espírito Santo"),
	RJ(33,"Rio de Janeiro"),
	SP(35,"São Paulo"),
	PR(41,"Paraná"),
	SC(42,"Santa Catarina"),
	RS(43,"Rio Grande do Sul"),
	MS(50,"Mato Grosso do Sul"),
	MT(51,"Mato Grosso"),
	GO(52,"Goiás"),
	DF(53,"Distrito Federal");

	private final Integer codigo;
	
	private final String nome;

	private Estado(Integer codigo,String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public String getNome(){
		return nome;
	}
	
	public static Estado toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (Estado x : Estado.values()) { 
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Estado invalido:" + codigo);
	}
}
