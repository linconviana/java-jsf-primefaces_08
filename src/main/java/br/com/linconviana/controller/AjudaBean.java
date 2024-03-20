package br.com.linconviana.controller;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.linconviana.entities.Empresa;
import br.com.linconviana.service.EmpresaService;

@Named(value = "ajudaMB")
@ViewScoped
//https://www.youtube.com/watch?v=TMCWEwyhEFM
//https://github.com/algaworks/videoaula-jsf-prettyfaces
//https://www.ocpsoft.org/prettyfaces/
//https://www.guj.com.br/t/url-amigaveis-com-prettyfaces/199295
//https://stackoverflow.com/questions/15024740/using-path-parameters-in-view-id-with-prettyfaces
//https://github.com/ocpsoft/prettyfaces-examples/blob/master/blog/src/main/webapp/index.xhtml
public class AjudaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idUrlParams;
	
	@Inject
	private EmpresaService service;
	
	private Empresa empresa = new Empresa();
	
	/*@PostConstruct
	public void listarTodasEmpresas() {
		empresa = service.findById(1L);
	}*/

	public void iniciar() {
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idStr = params.get("id");
		if(idStr != null) {			
			this.idUrlParams = Long.parseLong(idStr);
			empresa = service.findById(idUrlParams);
		}
	}
	
	public void salvar() {
		var teste = 0;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Long getIdUrlParams() {
		return idUrlParams;
	}

	public void setIdUrlParams(Long idUrlParams) {
		this.idUrlParams = idUrlParams;
	}
}
