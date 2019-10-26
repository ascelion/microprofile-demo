package ascelion.kalah.shared.persistence;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import static java.util.Collections.emptyMap;
import static javax.persistence.spi.PersistenceProviderResolverHolder.getPersistenceProviderResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dependent
public class PersistenceBean {
	static private final Logger LOG = LoggerFactory.getLogger(PersistenceBean.class);

	@Inject
	private PersistenceUnitInfo pui;

	private Class<? extends PersistenceProvider> providerType;

	@Produces
	@Dependent
	EntityManagerFactory entityManagerFactory(DataSource ds) {
		return getPersistenceProviderResolver()
				.getPersistenceProviders()
				.stream()
				.filter(this::selectedPersistenceProvider)
				.map(this::createEntityManagerFactory)
				.findFirst()
				.orElseThrow(() -> new PersistenceException("No Persistence provider for EntityManagerFactory named " + this.pui.getPersistenceUnitName()));
	}

	void closeEntityManagerFactory(@Disposes EntityManagerFactory emf) {
		emf.close();
	}

	@Produces
	@Dependent
	EntityManager entityManager(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}

	void closeEntityManager(@Disposes EntityManager em) {
		em.close();
	}

	private boolean selectedPersistenceProvider(PersistenceProvider provider) {
		return this.providerType == null || this.providerType.isInstance(provider);
	}

	private EntityManagerFactory createEntityManagerFactory(PersistenceProvider provider) {
		return provider.createContainerEntityManagerFactory(this.pui, emptyMap());
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void postConstruct() {
		final String className = this.pui.getPersistenceProviderClassName();

		if (className != null) {
			try {
				this.providerType = ((Class<? extends PersistenceProvider>) this.pui.getClassLoader().loadClass(className));
			} catch (final ClassNotFoundException e) {
				LOG.warn("Cannot load {}, using the default persistence provider, if any", className);
			}
		}
	}
}
