package br.com.linconviana.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.linconviana.converter.ConverteRamoAtividade;
import br.com.linconviana.entities.Empresa;
import br.com.linconviana.entities.RamoAtividade;
import br.com.linconviana.entities.TipoEmpresa;
import br.com.linconviana.report.CreateReportPDF;
import br.com.linconviana.report.ModelReport;
import br.com.linconviana.service.EmpresaService;
import br.com.linconviana.service.RamoAtividadeService;
import br.com.linconviana.utils.Message;
import net.sf.jasperreports.engine.JRException;

@Named(value = "empresaMB")
@ViewScoped
//@RequestScoped
//@SessionScoped
//@ApplicationScoped
//@ManagedBean
public class EmpresaController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/// :: Configura os parâmetros do relatorio.
	Map<String, Object> parametros = new HashMap<>();
	
	private ModelReport modelReport;
	
	@Inject
	private EmpresaService service;
	
	@Inject
	private RamoAtividadeService ramoAtividadeservice;
	
	@Inject
	private Empresa empresa = new Empresa();

	private List<Empresa> listaEmpresas;
	private List<RamoAtividade> listaRamoAtividades;
	
	private String termoPesquisa;
	
	private ConverteRamoAtividade ramoAtividadeConverter;
	
	private String tipoCadastro = "SAVE";
	
	private Integer totalRegistros;
		
	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	@PostConstruct
	public void listarTodasEmpresas() {
		listaEmpresas = service.findAll();
		listaRamoAtividades = ramoAtividadeservice.findAll();
		tipoCadastro = "SAVE";
		
		totalRegistros = listaEmpresas.size();
	}
  
	public void pesquisarEmpresa() {

		listaEmpresas = service.pesquisar(termoPesquisa);
		
		if(listaEmpresas.isEmpty()) {
			Message.error("Não existe uma empresa com o termo: ".concat(termoPesquisa));
		}
		
		/// :: Atualizar tabela e mensagem
		PrimeFaces.current().ajax().update(Arrays.asList("form:empresaDataTable","form:messagesTabela"));
	}
	
	public void changeTitleButton(String type) {
	
		if(type.equals("SAVE")) {
			tipoCadastro = "SAVE";
		} else {
			tipoCadastro = "UPDATE";
		}
	}
	
	public void excluirEmpresa() {
		service.delete(empresa);
		
		empresa = new Empresa();
		listarTodasEmpresas();
		
		Message.info("Empresa excluida com sucesso!");
		
		PrimeFaces.current().ajax().update(Arrays.asList("form:empresaDataTable","form:messagesTabela"));
	}
		
	public void salvar() {

		Long id = empresa.getId();
				
		service.save(empresa);
		
		empresa = new Empresa();
		listarTodasEmpresas();
		
		if(id == null) {
			Message.info("Empresa cadastrada com sucesso!");
		} else {
			Message.info("Empresa atualizada com sucesso!");
		}
		
		/// :: Atualizar tabela e mensagem
		PrimeFaces.current().ajax().update(Arrays.asList("form:empresaDataTable","form:messagesTabela"));
	}
	
	public String ajuda() {
		return "Ajuda?faces-redirect=true";
	}
	
	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}
	
	public void setListaEmpresas(List<Empresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}
	
	public List<RamoAtividade> getListaRamoAtividades() {
		return listaRamoAtividades;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}	
		
	public TipoEmpresa[] getSelectTiposEmpresa() {
		return TipoEmpresa.values();
	}
	
	public ConverteRamoAtividade getRamoAtividadeConverter() {
		return ramoAtividadeConverter;
	}
		
	public boolean isEmpresaSelecionada() {
		return empresa != null && empresa.getId() != null;
	}
	
	public String getTipoCadastro() {
		return tipoCadastro;
	}

	public void setTipoCadastro(String tipoCadastro) {
		this.tipoCadastro = tipoCadastro;
	}

	public void handleClose() {
		empresa = new Empresa();
	}
	
	public void relatorios() throws JRException, IOException {

		///https://www.youtube.com/watch?v=oTigbEyBelg
		try {
			
			CreateReportPDF createReport= new CreateReportPDF();
			
			parametros.put("empresa", empresa.getNomeFantasia());
			parametros.put("cnpj", empresa.getCnpj());
			parametros.put("data", empresa.getDataFundacao());
			parametros.put("logo", null);
			
			modelReport = new ModelReport();
			
			String reportName = "empresas";
			String folder = "empresas\\";
			//String folder = "empresas/";
			
			modelReport.setFolder(folder);
			modelReport.setNomeRelatorio(reportName);
			modelReport.setLista(listaEmpresas);
			modelReport.setParametros(parametros);
			
			createReport.gerarRelatorioEditado(modelReport);
			
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	//private Converter ramoAtividadeConverter;
	
	/*public Converter getRamoAtividadeConverter() {
		return ramoAtividadeConverter;
	}*/
	
	/// :: Utilizando Auto Complete
	/*public List<RamoAtividade> completarRamoAtividade(String termo){
		
		List<RamoAtividade> listaRamoAtividades = ramoAtividadeservice.pesquisar(termo);
		ramoAtividadeConverter = new ConverteRamoAtividade(listaRamoAtividades);
		
		return listaRamoAtividades;
	}*/
}
