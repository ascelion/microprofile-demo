package ascelion.kalah.gradle;

import java.util.Properties;

import org.gradle.api.internal.PropertiesTransformer;
import org.gradle.plugins.ide.internal.generator.PropertiesPersistableConfigurationObject;

public class EclipseCorePrefs extends PropertiesPersistableConfigurationObject {

	public EclipseCorePrefs(PropertiesTransformer transformer) {
		super(transformer);
	}

	@Override
	protected void store(Properties properties) {
	}

	@Override
	protected void load(Properties properties) {
	}

	@Override
	protected String getDefaultResourceName() {
		return "default.org.eclipse.jdt.core.prefs";
	}

}
