package br.com.linconviana.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelReport{


	private List<?> lista = new ArrayList<>();
	private Map<String, Object> parametros = new HashMap<>();
	private String nomeRelatorio;
	private String folder;
	
	/*public ModelReport(List<?> lista, Map<String, Object> parametros,
			String nomeRelatorio, String folder) {
		this.lista = lista;
		this.parametros = parametros;
		this.nomeRelatorio = nomeRelatorio;
		this.folder = folder;
	}*/
	
	public List<?> getLista() {
		return lista;
	}
	
	public void setLista(List<?> lista) {
		this.lista = lista;
	}
	
	public Map<String, Object> getParametros() {
		return parametros;
	}
	
	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}
	
	public String getNomeRelatorio() {
		return nomeRelatorio;
	}
	
	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}
	
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}	
	
	
}
