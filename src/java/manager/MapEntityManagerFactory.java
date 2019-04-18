package manager;

import javax.persistence.EntityManagerFactory;

public class MapEntityManagerFactory {

	private static MapEntityManagerFactory instance = null;
	private EntityManagerFactory emf;

	protected MapEntityManagerFactory() {
	}

	public static MapEntityManagerFactory getInstance() {
		if (instance == null) {
			instance = new MapEntityManagerFactory();
		}
		return instance;
	}
	
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return this.emf;
	}

}
