package ascelion.kalah.gradle;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.plugins.ide.api.PropertiesGeneratorTask;

@Getter
@Setter
public class EclipseAptTask extends PropertiesGeneratorTask<EclipseApt> {

	@Override
	protected void configure(EclipseApt apt) {
		apt.setEnabled(true);
		apt.setReconcileEnabled(true);

		getProject().getExtensions().getByType(SourceSetContainer.class)
				.all(set -> setGeneratedOutput(apt, set));
	}

	@Override
	protected EclipseApt create() {
		return new EclipseApt(getTransformer());
	}

	private void setGeneratedOutput(EclipseApt apt, SourceSet set) {
		final String gen = getProject().relativePath(set.getOutput().getGeneratedSourcesDirs().getSingleFile());

		set.java(sds -> sds.srcDir(gen));

		switch (set.getName()) {
			case "main":
				apt.setGenSrcDir(gen);
			break;
			case "test":
				apt.setGenTestSrcDir(gen);
			break;
		}
	}
}
