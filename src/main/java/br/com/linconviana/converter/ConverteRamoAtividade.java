package br.com.linconviana.converter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.linconviana.entities.RamoAtividade;
import br.com.linconviana.repositories.RamoAtividadeRepository;

//@FacesConverter(value = "converteRamoAtividade", forClass = RamoAtividade.class
@FacesConverter(value = "converteRamoAtividade")
public class ConverteRamoAtividade implements Converter {
	
	public ConverteRamoAtividade() {}
	
	private RamoAtividadeRepository repository = new RamoAtividadeRepository();
	
	private List<RamoAtividade> listaRamoAtividade;
	
	public ConverteRamoAtividade(List<RamoAtividade> listaRamoAtividade) {
		this.listaRamoAtividade = listaRamoAtividade;
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigo) {

		if(codigo == null) {
			return null;
		}
		
		/*Long id = Long.valueOf(codigo);
		
		RamoAtividade ramoAtividade = repository.findById(id);
		if(id.equals(ramoAtividade.getId())) {
			return ramoAtividade;
		}

		return null;*/
		
		return this.getAttributesFrom(component).get(codigo);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if(object == null) {
			return null;
		}
		
		RamoAtividade ramoAtividade = (RamoAtividade) object;
		
		this.addAttribute(component, ramoAtividade);
		
		return ramoAtividade.getId().toString();
	}
	
	private Map<String, Object> getAttributesFrom(UIComponent component){
		return component.getAttributes();
	}

	private void addAttribute(UIComponent component, RamoAtividade ramoAtividade) {
		this.getAttributesFrom(component).put(ramoAtividade.getId().toString(), ramoAtividade);
	}
}
