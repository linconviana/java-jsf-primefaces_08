package br.com.linconviana.repositories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;

import br.com.linconviana.entities.Empresa;
import br.com.linconviana.entities.Cep;

public class EmpresaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	private static EntityManager manager = ConnectionFactory.getEntityManeger();

	/// :: Buscar todas as empresas
	@SuppressWarnings("unchecked")
	public List<Empresa> findAll() {
		return manager.createQuery("from Empresa order by id desc").getResultList();
	}

	/// :: Buscar por id
	public Empresa findById(Long id) {
		return manager.find(Empresa.class, id);
	}

	/// :: Buscar empresa por nome
	public List<Empresa> pesquisar(String nomeFantasia) {
		TypedQuery<Empresa> query = manager
				.createQuery("from Empresa where lower(nomeFantasia) like lower(concat(:nomeFantasia))", Empresa.class);
		query.setParameter("nomeFantasia", nomeFantasia + "%");
		return query.getResultList();
	}

	/// :: Salvar e Atualizar
	public Empresa save(Empresa entity) {

		manager.getTransaction().begin();
		entity = manager.merge(entity);
		manager.getTransaction().commit();

		// ConnectionFactory.closeEntityManager(manager);

		return entity;
	}

	/// :: Delete
	public void delete(Empresa entity) {

		try {
			entity = findById(entity.getId());

			manager.getTransaction().begin();

			manager.remove(entity);

			manager.getTransaction().commit();

			// ConnectionFactory.closeEntityManager(manager);

		} catch (Exception e) {
			manager.getTransaction().rollback();
		}
	}

	///https://pt.stackoverflow.com/questions/532046/query-jpql-para-retornar-somente-um-dos-campos
	//https://www.guj.com.br/t/como-retornar-somente-um-campo-usando-jpql-no-spring-boot/421646/3 - Tuple
	public List<Empresa> todasEmpresasBasico() {
		TypedQuery<Tuple> consulta = manager.createQuery(
			"select emp.id, emp.nomeFantasia FROM Empresa emp order by id desc", Tuple.class
		);
		List<Tuple> resultsTuple = consulta.getResultList();
				
		return resultsTuple.stream().map(tuple -> new Empresa(tuple))
				.collect(Collectors.toList());
	}
	
	/*
	 public List<EmpresaBasic> todasEmpresasBasico() {
		TypedQuery<Tuple> consulta = manager.createQuery(
			"select emp.id, emp.nomeFantasia FROM Empresa emp order by id desc", Tuple.class
		);
		List<Tuple> resultsTuple = consulta.getResultList();
		
		List<EmpresaBasic> listaEmpresa = new ArrayList<>();
		
		listaEmpresa = resultsTuple.stream().map(tuple -> new EmpresaBasic(tuple))
				.collect(Collectors.toList());
		return listaEmpresa;
	}*/
}
