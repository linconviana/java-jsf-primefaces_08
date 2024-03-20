package br.com.linconviana.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.linconviana.utils.Message;
import javax.annotation.PostConstruct;

@Named(value = "testeMB")
@ViewScoped
public class ModalFormBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String razao;
	private String fantasia;
	private String cnpj;	
	
	/*@PostConstruct
	public void iniciar() {
		setRazao("");
		setFantasia("");
		setCnpj("12.456.789/0001-99");
	}*/
	
	public void mensagem() {
		Message.info("Teste  mensagem! -> " + getRazao());
	}
	
	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}




