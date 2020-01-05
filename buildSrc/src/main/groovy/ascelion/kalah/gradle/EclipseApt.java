package ascelion.kalah.gradle;

import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.internal.PropertiesTransformer;
import org.gradle.plugins.ide.internal.generator.PropertiesPersistableConfigurationObject;

@Getter
@Setter
public class EclipseApt extends PropertiesPersistableConfigurationObject {
	static private final String PREFIX = "org.eclipse.jdt.apt.";

	static private Optional<String> get(Properties properties, String name) {
		return Optional.ofNullable(properties.getProperty(PREFIX + name));
	}

	static private void set(Properties properties, String name, Object value) {
		properties.setProperty(PREFIX + name, Objects.toString(value, ""));
	}

	private boolean enabled;
	private String genSrcDir;
	private String genTestSrcDir;
	private boolean reconcileEnabled;

	public EclipseApt(PropertiesTransformer transformer) {
		super(transformer);
	}

	@Override
	protected void load(Properties properties) {
		this.enabled = get(properties, "aptEnabled").map(Boolean::valueOf).orElse(true);
		this.genSrcDir = get(properties, "genSrcDir").orElse(".apt_generated");
		this.genTestSrcDir = get(properties, "genTestSrcDir").orElse(".apt_generated_tests");
		this.reconcileEnabled = get(properties, "reconcileEnabled").map(Boolean::valueOf).orElse(true);
	}

	@Override
	protected void store(Properties properties) {
		set(properties, "aptEnabled", this.enabled);
		set(properties, "genSrcDir", this.genSrcDir);
		set(properties, "genTestSrcDir", this.genTestSrcDir);
		set(properties, "reconcileEnabled", this.reconcileEnabled);
	}

	@Override
	protected String getDefaultResourceName() {
		return "default.org.eclipse.jdt.apt.core.prefs";
	}
}
