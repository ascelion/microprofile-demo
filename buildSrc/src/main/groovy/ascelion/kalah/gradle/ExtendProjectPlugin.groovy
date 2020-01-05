package ascelion.kalah.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.TaskProvider
import org.gradle.plugins.ide.eclipse.GenerateEclipseProject
import org.gradle.plugins.ide.eclipse.model.EclipseModel
import org.gradle.plugins.ide.eclipse.model.SourceFolder

class ExtendProjectPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.metaClass.stringProperty = { String name, String defVal ->
			project.ext.properties.getOrDefault( name, defVal )
		}
		project.metaClass.booleanProperty = { String name, boolean defVal ->
			String prp = project.stringProperty( name, Boolean.toString(defVal) )

			return prp.empty || Boolean.valueOf( prp )
		}
		project.metaClass.integerProperty = { String name, int defVal ->
			return Integer.valueOf( project.stringProperty( name, Integer.toString(defVal) ) )
		}

		project.metaClass.stringProperty = { String name ->
			project.stringProperty( name, null )
		}
		project.metaClass.booleanProperty = { String name ->
			project.booleanProperty( name, false )
		}
		project.metaClass.integerProperty = { String name ->
			project.integerProperty( name, 0 )
		}

		project.plugins.withType(JavaPlugin) {
			TaskProvider<EclipseFactorypathTask> efp = project.tasks.register("eclipseFactorypath", EclipseFactorypathTask)

			efp.configure(new Action<EclipseFactorypathTask>() {
						@Override
						public void execute(EclipseFactorypathTask t) {
							t.setInputFile(project.file('.factorypath'))
							t.setOutputFile(project.file('.factorypath'))
						}
					})

			TaskProvider<EclipseAptTask> apt = project.tasks.register("eclipseApt", EclipseAptTask)

			apt.configure(new Action<EclipseAptTask>() {
						@Override
						public void execute(EclipseAptTask t) {
							t.setOutputFile(project.file(".settings/org.eclipse.jdt.apt.core.prefs"))
						}
					})

			project.tasks.withType(GenerateEclipseProject).all {
				it.dependsOn efp
				it.dependsOn apt
			}
			project.tasks.all {
				if( name == 'nothing' ) {
					it.dependsOn efp
					it.dependsOn apt
				}
			}

			EclipseModel eclipse = project.extensions.getByType(EclipseModel)

			eclipse.classpath {
				file.beforeMerged { cp ->
					sourceSets.all { set ->
						def cfg = project.configurations.getByName( set.annotationProcessorConfigurationName )

						if( cfg.dependencies.size() > 0 ) {
							def src = project.file("${project.buildDir}/generated/sources/annotationProcessor/java/${set.name}")

							if( src.exists() ) {
								def dst = set.output.classesDirs.first()

								cp.entries.add( new SourceFolder(project.relativePath(src), project.relativePath(dst)) )
							}
						}
					}
				}
			}
		}
	}
}
