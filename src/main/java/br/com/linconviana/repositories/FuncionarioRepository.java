package br.com.linconviana.repositories;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.linconviana.entities.Funcionario;


public class FuncionarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	private static EntityManager manager = ConnectionFactory.getEntityManeger();
	
	/// :: Buscar todas as Funcionarios
	@SuppressWarnings("unchecked")
	public List<Funcionario> findAll(){
		return manager.createQuery("from Funcionario order by id desc").getResultList();
	}
	
	/// :: Buscar por id
	public Funcionario findById(Long id) {
		return manager.find(Funcionario.class, id);
	}
	
	/// :: Buscar Funcionario por nome
	public List<Funcionario> pesquisar(String nome){
		TypedQuery<Funcionario> query = manager.createQuery("from Funcionario where lower(nome) like lower(concat(:nome))", Funcionario.class);
		query.setParameter("nome", nome + "%");		
		return query.getResultList();
	}
	
	/// :: Salvar e Atualizar
	public Funcionario save(Funcionario entity) {
		
		manager.getTransaction().begin();		
		entity = manager.merge(entity);
		manager.getTransaction().commit();
		
		//ConnectionFactory.closeEntityManager(manager);
		
		return entity;		
	}
	
	/// :: Delete
	public void delete(Funcionario entity) {		

		try {
			entity = findById( entity.getId());
			
			manager.getTransaction().begin();
			
			manager.remove(entity);
			
			manager.getTransaction().commit();
			
			//ConnectionFactory.closeEntityManager(manager);
			
		} catch (Exception e) {
			manager.getTransaction().rollback();
		}
	}
}
