package br.com.linconviana.entities;

public enum TipoFuncaoFuncionario {

	RH("Recursos Humanos"),
	ST("System Team"),
	DEV_JUNIOR("Desenvolvedor Junior"),
	DEV_PLENO("Desenvolvedor Pleno"),
	DEV_SENIOR("Desenvolvedor Senior"),
	DEV_ARCHITECT("Arquiteto de Software"),
	AM("Agile Master"),
	TECH("Tech Lead"),
	RECRUTER("Tech Recruter");
	
	private String descricao;
	
	TipoFuncaoFuncionario(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}		
}
