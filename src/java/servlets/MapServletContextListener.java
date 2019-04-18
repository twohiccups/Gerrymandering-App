package servlets;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MapServletContextListener implements ServletContextListener {

	private EntityManagerFactory entityManagerFactory;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		entityManagerFactory = Persistence.createEntityManagerFactory("localhost:8080");
		context.setAttribute("emf", entityManagerFactory);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		entityManagerFactory.close();
	}
}