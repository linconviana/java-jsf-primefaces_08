package br.com.linconviana.repositories;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import br.com.linconviana.entities.RamoAtividade;

public class RamoAtividadeRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	private static EntityManager manager = ConnectionFactory.getEntityManeger();
	
	/// :: Buscar todas as empresas
	@SuppressWarnings("unchecked")
	public List<RamoAtividade> findAll(){
		return manager.createQuery("from RamoAtividade order by id desc").getResultList();
	}
	
	/// :: Buscar por id
	public RamoAtividade findById(Long id) {
		return manager.find(RamoAtividade.class, id);
	}
	
	/// :: Buscar por nome
	public List<RamoAtividade> pesquisar(String descricao){
		
		TypedQuery<RamoAtividade> query = manager.createQuery(
				"from RamoAtividade where lower(descricao) like lower(concat(:descricao))", RamoAtividade.class
		);
		query.setParameter("descricao", descricao + "%");		
		return query.getResultList();
	}
	
	/*
	 * public List<RamoAtividade> pesquisar(String descricao){
		
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<RamoAtividade> criteriaQuery = criteriaBuilder.createQuery(RamoAtividade.class);		
		Root<RamoAtividade> root = criteriaQuery.from(RamoAtividade.class);		
		criteriaQuery.select(root);		
		criteriaQuery.where(criteriaBuilder.like(root.get("descricao"), descricao + "%"));
		
		TypedQuery<RamoAtividade> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}*/
	
	/// :: Salvar e Atualizar
	public RamoAtividade save(RamoAtividade entity) {
		
		manager.getTransaction().begin();		
		entity = manager.merge(entity);
		manager.getTransaction().commit();
		
		//ConnectionFactory.closeEntityManager(manager);
		
		return entity;
	}
	
	/// :: delete
	public void delete(RamoAtividade entity) {		
		
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
