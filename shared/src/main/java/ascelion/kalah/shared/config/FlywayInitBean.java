package ascelion.kalah.shared.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.Configuration;

@ApplicationScoped
class FlywayInitBean {
	@Produces
	@Singleton
	Configuration configuration(DataSource ds) {
		return Flyway.configure()
				.dataSource(ds)
				.locations("db");
	}
}
