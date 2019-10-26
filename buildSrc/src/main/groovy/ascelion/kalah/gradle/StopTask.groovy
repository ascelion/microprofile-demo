package ascelion.kalah.gradle

class StopTask extends DockerComposeTask {
	StopTask() {
		super("stop")

		ignoreFailures = true
	}
}
