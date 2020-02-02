package ascelion.kalah.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.TaskProvider
import org.gradle.plugins.ide.eclipse.GenerateEclipseProject
import org.gradle.plugins.ide.eclipse.model.EclipseModel

class ExtendProjectPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.apply( plugin: 'lifecycle-base' )
		project.apply( plugin: 'eclipse' )

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
			TaskProvider<EclipseFormatterTask> fmt = project.tasks.register("eclipseFormatter", EclipseFormatterTask)
			TaskProvider<EclipseFactorypathTask> efp = project.tasks.register("eclipseFactorypath", EclipseFactorypathTask)

			efp.configure(new Action<EclipseFactorypathTask>() {
						@Override
						public void execute(EclipseFactorypathTask t) {
							t.setInputFile(project.file(".factorypath"))
							t.setOutputFile(project.file(".factorypath"))
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
				it.dependsOn fmt
			}

			EclipseModel eclipse = project.extensions.getByType(EclipseModel)

			eclipse.synchronizationTasks(efp, apt, fmt)
		}
	}
}
