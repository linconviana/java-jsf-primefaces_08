package br.com.linconviana.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.linconviana.entities.RamoAtividade;
import br.com.linconviana.repositories.RamoAtividadeRepository;

public class RamoAtividadeService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private RamoAtividadeRepository repository;
	
	public List<RamoAtividade> findAll(){		
		return repository.findAll();
	}
	
	public RamoAtividade findById(Long id) {
		return repository.findById(id);
	}
	
	public List<RamoAtividade> pesquisar(String nome){
		return repository.pesquisar(nome);
	}
	
	@Transactional
	public RamoAtividade save(RamoAtividade entity) {
		entity = repository.save(entity);
		return entity;
	}
	
	@Transactional
	public void delete(RamoAtividade entity) {
		repository.delete(entity);
	}
}
