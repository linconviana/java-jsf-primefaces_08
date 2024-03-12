package br.com.linconviana.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.linconviana.entities.RamoAtividade;
import br.com.linconviana.entities.TipoEmpresa;
import br.com.linconviana.service.RamoAtividadeService;
import br.com.linconviana.utils.Message;

@Named(value = "ramoAtividadeMB")
@ViewScoped
public class RamoAtividadeController implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private RamoAtividadeService service;

	@Inject
	private RamoAtividade ramoAtividade;
	
	private List<RamoAtividade> listaRamoAtividades;
	
	private String termoPesquisa;

	private String tipoCadastro = "SAVE";
		
	@PostConstruct
	public void listarTodos() {
		listaRamoAtividades = service.findAll();
		ramoAtividade = new RamoAtividade();
		tipoCadastro = "SAVE";
	}
  
	public void pesquisar() {

		listaRamoAtividades = service.pesquisar(termoPesquisa);
		
		if(listaRamoAtividades.isEmpty()) {
			Message.error("Não existe um ramo de atividade com o termo: ".concat(termoPesquisa));
		}
		
		/// :: Atualizar tabela e mensagem
		PrimeFaces.current().ajax().update(Arrays.asList("form:dataTable","form:messagesTabela"));
	}
	
	public void changeTitleButton(String type) {
	
		if(type.equals("SAVE")) {
			tipoCadastro = "SAVE";
		} else {
			tipoCadastro = "UPDATE";
		}
	}
	
	public void excluir() {
		service.delete(ramoAtividade);
		
		ramoAtividade = new RamoAtividade();
		listarTodos();
		
		Message.info("Ramo Atividade excluida com sucesso!");
		
		PrimeFaces.current().ajax().update(Arrays.asList("form:dataTable","form:messagesTabela"));
	}
		
	public void salvar() {

		Long id = ramoAtividade.getId();
				
		service.save(ramoAtividade);
		
		ramoAtividade = new RamoAtividade();
		listarTodos();
		
		if(id == null) {
			Message.info("Ramo Atividade cadastrada com sucesso!");
		} else {
			Message.info("Ramo Atividade atualizada com sucesso!");
		}
		
		/// :: Atualizar tabela e mensagem
		PrimeFaces.current().ajax().update(Arrays.asList("form:dataTable","form:messagesTabela"));
	}
	
	public RamoAtividade getRamoAtividade() {
		return ramoAtividade;
	}

	public void setRamoAtividade(RamoAtividade ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}

	public List<RamoAtividade> getListaRamoAtividades() {
		return listaRamoAtividades;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}	
		
	public String getTipoCadastro() {
		return tipoCadastro;
	}

	public void setTipoCadastro(String tipoCadastro) {
		this.tipoCadastro = tipoCadastro;
	}	
}
