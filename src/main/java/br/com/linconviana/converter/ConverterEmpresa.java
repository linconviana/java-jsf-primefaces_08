package br.com.linconviana.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.linconviana.entities.Empresa;
import br.com.linconviana.repositories.EmpresaRepository;
import br.com.linconviana.repositories.RamoAtividadeRepository;

@FacesConverter(value = "converterEmpresa")
public class ConverterEmpresa implements Converter {
	
	public ConverterEmpresa() {}
	
	private EmpresaRepository repository = new EmpresaRepository();
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigo) {

		if(codigo == null) {
			return null;
		}
					
		return this.getAttributesFrom(component).get(codigo);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if(object == null) {
			return null;
		}
		
		Empresa empresa = (Empresa) object;
		
		this.addAttribute(component, empresa);
		
		return empresa.getId().toString();
	}
	
	private Map<String, Object> getAttributesFrom(UIComponent component){
		return component.getAttributes();
	}

	private void addAttribute(UIComponent component, Empresa empresa) {
		this.getAttributesFrom(component).put(empresa.getId().toString(), empresa);
	}
}
