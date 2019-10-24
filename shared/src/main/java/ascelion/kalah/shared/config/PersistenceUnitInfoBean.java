package ascelion.kalah.shared.config;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import ascelion.microprofile.config.ConfigPrefix;
import ascelion.microprofile.config.ConfigValue;

import static java.util.Collections.emptyList;
import static javax.persistence.spi.PersistenceUnitTransactionType.JTA;
import static javax.persistence.spi.PersistenceUnitTransactionType.RESOURCE_LOCAL;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.config.Config;

@Getter
@Dependent
@ConfigPrefix("jpa")
public class PersistenceUnitInfoBean implements PersistenceUnitInfo {
	private static final String JPA_PROPERTIES = "jpa.properties.";

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
	private final Properties properties = new Properties();
	private String persistenceXMLSchemaVersion;
	private final ClassLoader classLoader = getClass().getClassLoader();

	@Inject
	private DataSource ds;
	@Inject
	private Config config;

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
		return this.transactionType == JTA ? this.ds : null;
	}

	@Override
	public DataSource getNonJtaDataSource() {
		return this.transactionType == RESOURCE_LOCAL ? this.ds : null;
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
		for (final String name : this.config.getPropertyNames()) {
			if (name.startsWith(JPA_PROPERTIES)) {
				this.properties.put(name.substring(JPA_PROPERTIES.length()), this.config.getValue(name, String.class));
			}
		}

		this.persistenceUnitRootUrl = PersistenceUnitInfoBean.class.getProtectionDomain().getCodeSource().getLocation();

		try {
			final Enumeration<URL> pds = getClass().getClassLoader().getResources(this.persistenceXML);

			while (pds.hasMoreElements()) {
				final String pd = pds.nextElement().toExternalForm();

				this.jarFileUrls.add(new URL(pd.substring(0, pd.length() - this.persistenceXML.length())));
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
