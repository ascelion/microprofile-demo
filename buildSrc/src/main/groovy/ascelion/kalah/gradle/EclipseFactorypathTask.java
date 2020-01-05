package ascelion.kalah.gradle;

import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.plugins.ide.api.XmlGeneratorTask;

public class EclipseFactorypathTask extends XmlGeneratorTask<EclipseFactorypath> {

	@Override
	protected void configure(EclipseFactorypath path) {
		getProject().getExtensions().getByType(SourceSetContainer.class)
				.all(set -> addProcessorPath(path, set));
	}

	private void addProcessorPath(EclipseFactorypath path, SourceSet set) {
		set.getAnnotationProcessorPath()
				.filter(file -> file.isFile())
				.forEach(file -> {
					path.getEntries().add(new EclipseFactorypathEntry(file));
				});
		set.getCompileClasspath()
				.filter(file -> file.isFile())
				.forEach(file -> {
					path.getEntries().add(new EclipseFactorypathEntry(file));
				});
	}

	@Override
	protected EclipseFactorypath create() {
		return new EclipseFactorypath(getXmlTransformer());
	}
}
