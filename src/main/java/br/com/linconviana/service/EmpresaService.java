package br.com.linconviana.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.linconviana.entities.Empresa;
import br.com.linconviana.entities.Cep;
import br.com.linconviana.repositories.EmpresaRepository;

public class EmpresaService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaRepository repository;
	
	public List<Empresa> findAll(){		
		return repository.findAll();
	}
	
	public Empresa findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Empresa> pesquisar(String nome){
		return repository.pesquisar(nome);
	}
	
	@Transactional
	public Empresa save(Empresa entity) {
		entity = repository.save(entity);
		return entity;
	}
	
	@Transactional
	public void delete(Empresa entity) {
		repository.delete(entity);
	}
	
	public List<Empresa> todasEmpresasBasico() {
		return repository.todasEmpresasBasico();
	}
}
