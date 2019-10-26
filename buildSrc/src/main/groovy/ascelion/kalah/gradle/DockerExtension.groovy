package ascelion.kalah.gradle

import groovy.transform.PackageScope
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.util.ConfigureUtil

class DockerExtension {
	@PackageScope
	Project project
	File outputDirectory
	FileCollection templates

	final def configuration = new DockerConfiguration()

	DockerExtension( Project project ) {
		this.project = project

		templates = project.files()
		outputDirectory = project.file( "${project.buildDir}/docker" )

		configuration.network = 'eth'
		configuration.application = project.name

		configuration.name(project.rootProject.name)
		configuration.scalable(true)
		configuration.tagName(project.version)
		configuration.repository('')
	}

	String getName() {
		return configuration.name
	}
	void setName(String name) {
		this.configuration.name(name)
	}
	boolean isScalable() {
		return configuration.scalable
	}
	void setScalable(boolean scalable) {
		configuration.scalable(scalable)
	}
	String getTagName() {
		return configuration.tagName
	}
	void setTagName(String tagName) {
		this.configuration.tagName(tagName)
	}
	String getRepository() {
		return configuration.repository
	}
	void setRepository(String repository) {
		this.configuration.repository(repository)
	}

	void templates( FileCollection templates ) {
		this.templates += templates
	}

	File getOutputDirectory() {
		return outputDirectory
	}

	def configuration( Closure closure ) {
		closure.delegate = this.project

		ConfigureUtil.configure(closure, configuration)
	}
}

