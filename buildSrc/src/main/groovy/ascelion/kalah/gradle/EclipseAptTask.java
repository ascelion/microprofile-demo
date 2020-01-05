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
		getProject().getExtensions().getByType(SourceSetContainer.class)
				.all(set -> setGeneratedOutput(apt, set));
	}

	private void setGeneratedOutput(EclipseApt apt, SourceSet set) {
		switch (set.getName()) {
			case "main":
				apt.setGenSrcDir(getProject().relativePath(set.getOutput().getGeneratedSourcesDirs().getSingleFile()));
			break;
			case "test":
				apt.setGenTestSrcDir(getProject().relativePath(set.getOutput().getGeneratedSourcesDirs().getSingleFile()));
			break;
		}
	}

	@Override
	protected EclipseApt create() {
		return new EclipseApt(getTransformer());
	}
}
