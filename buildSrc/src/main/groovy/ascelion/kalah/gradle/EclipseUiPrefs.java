package ascelion.kalah.gradle;

import java.util.Properties;

import org.gradle.api.internal.PropertiesTransformer;
import org.gradle.plugins.ide.internal.generator.PropertiesPersistableConfigurationObject;

public class EclipseUiPrefs extends PropertiesPersistableConfigurationObject {

	public EclipseUiPrefs(PropertiesTransformer transformer) {
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
		return "default.org.eclipse.jdt.ui.prefs";
	}

}
