package br.com.linconviana.entities;

import java.io.Serializable;

public class Cep implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String logradouro;
	private String bairro;
	private String localidade;
	private String uf; 
	
	public Cep() {}

	public Cep(String logradouro, String bairro, String localidade, String uf) {
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}
