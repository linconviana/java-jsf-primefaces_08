package br.com.linconviana.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.linconviana.entities.Funcionario;
import br.com.linconviana.repositories.FuncionarioRepository;

public class FuncionarioService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioRepository repository;
	
	public List<Funcionario> findAll(){		
		return repository.findAll();
	}
	
	public Funcionario findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Funcionario> pesquisar(String nome){
		return repository.pesquisar(nome);
	}
	
	//@Transactional
	public Funcionario save(Funcionario entity) {
		entity = repository.save(entity);
		return entity;
	}
	
	@Transactional
	public void delete(Funcionario entity) {
		repository.delete(entity);
	}
}
