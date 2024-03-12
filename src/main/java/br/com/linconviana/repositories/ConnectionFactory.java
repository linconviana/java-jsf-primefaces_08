package br.com.linconviana.repositories;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class ConnectionFactory {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("CursoAlgaWorks");
	
	@Produces
	@RequestScoped
	public static EntityManager getEntityManeger() {		
		return factory.createEntityManager();
	}
	
	public static void closeEntityManager(@Disposes EntityManager manager) {
		manager.close();
	}
	
	/*public static void main(String[] args) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("CursoAlgaWorks");
		EntityManager entityManager = factory.createEntityManager();
		
		List<Empresa> lista = entityManager.createQuery("from Empresa", Empresa.class).getResultList();
		
		System.out.println(lista);
		
		entityManager.close();
		factory.close();
	}*/
}


