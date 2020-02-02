package ascelion.kalah.shared.persistence;

import static java.util.Collections.emptyMap;
import static javax.persistence.spi.PersistenceProviderResolverHolder.getPersistenceProviderResolver;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.Configuration;

@ApplicationScoped
class PersistenceBean {

	static private final Logger LOG = LoggerFactory.getLogger(PersistenceBean.class);

	private final PersistenceUnitInfo pui;
	private final Class<?> providerType;

	private final DataSource dataSource;

	@Inject
	PersistenceBean(PersistenceUnitInfo pui) {
		this.pui = pui;

		final String className = this.pui.getPersistenceProviderClassName();
		Class<?> clazz = null;

		if (className != null) {
			try {
				clazz = this.pui.getClassLoader().loadClass(className);
			} catch (final ClassNotFoundException e) {
				LOG.warn("Cannot load {}, using the default persistence provider, if any", className);
			}
		}

		this.providerType = clazz;

		if (pui.getTransactionType() == PersistenceUnitTransactionType.JTA) {
			this.dataSource = pui.getJtaDataSource();
		} else {
			this.dataSource = pui.getNonJtaDataSource();
		}
	}

	@Produces
	@ApplicationScoped
	Configuration configuration() {
		return Flyway.configure()
				.dataSource(this.dataSource)
				.locations("db");
	}

	@Produces
	@ApplicationScoped
	EntityManagerFactory entityManagerFactory() {
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
}
