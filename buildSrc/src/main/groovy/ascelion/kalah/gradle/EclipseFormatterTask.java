package ascelion.kalah.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.internal.PropertiesTransformer;
import org.gradle.api.tasks.TaskAction;

public class EclipseFormatterTask extends DefaultTask {
	private final PropertiesTransformer transformer = new PropertiesTransformer();

	@TaskAction
	void run() {
		final EclipseUiPrefs ui = new EclipseUiPrefs(this.transformer);
		final EclipseCorePrefs core = new EclipseCorePrefs(this.transformer);

		getProject().file(".settings").mkdirs();

		ui.loadDefaults();
		ui.store(getProject().file(".settings/org.eclipse.jdt.ui.prefs"));

		core.loadDefaults();
		core.store(getProject().file(".settings/org.eclipse.jdt.core.prefs"));
	}
}
