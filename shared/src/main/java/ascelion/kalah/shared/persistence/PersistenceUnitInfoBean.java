package ascelion.kalah.shared.persistence;

import static java.util.Collections.emptyList;
import static javax.persistence.spi.PersistenceUnitTransactionType.JTA;
import static javax.persistence.spi.PersistenceUnitTransactionType.RESOURCE_LOCAL;

import ascelion.config.api.ConfigPrefix;
import ascelion.config.api.ConfigValue;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.config.Config;

@Getter
@ApplicationScoped
@ConfigPrefix("jpa")
class PersistenceUnitInfoBean implements PersistenceUnitInfo {
	@Setter(onParam_ = @ConfigValue(required = false))
	private String unitName = "default";
	@Setter(onParam_ = @ConfigValue(required = false))
	private String persistenceProviderClassName;
	@Setter(onParam_ = @ConfigValue(required = false))
	private String persistenceXML = "META-INF/persistence.xml";
	@Setter(onParam_ = @ConfigValue(value = "properties.javax.persistence.transactionType", required = false))
	private PersistenceUnitTransactionType transactionType = RESOURCE_LOCAL;

	private final List<String> mappingFileNames = emptyList();
	private final List<URL> jarFileUrls = new ArrayList<>();
	private URL persistenceUnitRootUrl;
	private final List<String> managedClassNames = emptyList();
	private SharedCacheMode sharedCacheMode;
	private ValidationMode validationMode;
	@ConfigValue
	private Properties properties;
	private String persistenceXMLSchemaVersion;
	private final ClassLoader classLoader = getClass().getClassLoader();

	@Inject
	private Config config;
	@Inject
	@Named("default")
	private DataSource dataSource;

	@Override
	public boolean excludeUnlistedClasses() {
		return false;
	}

	@Override
	public String getPersistenceUnitName() {
		return this.unitName;
	}

	@Override
	public DataSource getJtaDataSource() {
		return this.transactionType == JTA ? this.dataSource : null;
	}

	@Override
	public DataSource getNonJtaDataSource() {
		return this.transactionType == RESOURCE_LOCAL ? this.dataSource : null;
	}

	@Override
	public void addTransformer(ClassTransformer transformer) {
	}

	@Override
	public ClassLoader getNewTempClassLoader() {
		return this.classLoader;
	}

	@PostConstruct
	private void postConstruct() {
		this.persistenceUnitRootUrl = PersistenceUnitInfoBean.class.getProtectionDomain().getCodeSource().getLocation();

		try {
			final Enumeration<URL> pds = getClass().getClassLoader().getResources(this.persistenceXML);

			while (pds.hasMoreElements()) {
				final String pd = pds.nextElement().toExternalForm();

				this.jarFileUrls.add(new URL(pd.substring(0, pd.length() - this.persistenceXML.length())));
			}

			this.jarFileUrls.remove(this.persistenceUnitRootUrl);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
