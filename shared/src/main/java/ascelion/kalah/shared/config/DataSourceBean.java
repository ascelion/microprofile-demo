package ascelion.kalah.shared.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import ascelion.microprofile.config.ConfigPrefix;
import ascelion.microprofile.config.ConfigValue;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@ApplicationScoped
@ConfigPrefix("database")
class DataSourceBean {
	private final HikariConfig hikari = new HikariConfig();

	@Produces
	DataSource datasource() {
		return new HikariDataSource(this.hikari);
	}

	public void setCatalog(@ConfigValue(required = false) String catalog) {
		this.hikari.setCatalog(catalog);
	}

	public void setIdleTimeout(@ConfigValue(required = false) long idleTimeoutMs) {
		this.hikari.setIdleTimeout(idleTimeoutMs);
	}

	public void setMaxLifetime(@ConfigValue(required = false) long maxLifetimeMs) {
		this.hikari.setMaxLifetime(maxLifetimeMs);
	}

	public void setMaximumPoolSize(@ConfigValue(required = false) int maxPoolSize) {
		this.hikari.setMaximumPoolSize(maxPoolSize);
	}

	public void setPassword(@ConfigValue(required = false) String password) {
		this.hikari.setPassword(password);
	}

	public void setUsername(@ConfigValue(required = false) String username) {
		this.hikari.setUsername(username);
	}

	public void setJdbcUrl(@ConfigValue(required = false) String jdbcUrl) {
		this.hikari.setJdbcUrl(jdbcUrl);
	}

	public void setSchema(@ConfigValue(required = false) String schema) {
		this.hikari.setSchema(schema);
	}

	public void setDataSourceClassName(@ConfigValue(required = false) String className) {
		this.hikari.setDataSourceClassName(className);
	}

	public void setDriverClassName(@ConfigValue(required = false) String driverClassName) {
		this.hikari.setDriverClassName(driverClassName);
	}

	public void setConnectionTimeout(@ConfigValue(required = false) long connectionTimeoutMs) {
		this.hikari.setConnectionTimeout(connectionTimeoutMs);
	}

	public void setLeakDetectionThreshold(@ConfigValue(required = false) long leakDetectionThresholdMs) {
		this.hikari.setLeakDetectionThreshold(leakDetectionThresholdMs);
	}

	public void setMinimumIdle(@ConfigValue(required = false) int minIdle) {
		this.hikari.setMinimumIdle(minIdle);
	}

	public void setValidationTimeout(@ConfigValue(required = false) long validationTimeoutMs) {
		this.hikari.setValidationTimeout(validationTimeoutMs);
	}

	public void setConnectionTestQuery(@ConfigValue(required = false) String connectionTestQuery) {
		this.hikari.setConnectionTestQuery(connectionTestQuery);
	}

	public void setDataSourceJNDI(@ConfigValue(required = false) String jndiDataSource) {
		this.hikari.setDataSourceJNDI(jndiDataSource);
	}

	public void setInitializationFailTimeout(@ConfigValue(required = false) long initializationFailTimeout) {
		this.hikari.setInitializationFailTimeout(initializationFailTimeout);
	}

	public void setIsolateInternalQueries(@ConfigValue(required = false) boolean isolate) {
		this.hikari.setIsolateInternalQueries(isolate);
	}

	public void setRegisterMbeans(@ConfigValue(required = false) boolean register) {
		this.hikari.setRegisterMbeans(register);
	}
}
