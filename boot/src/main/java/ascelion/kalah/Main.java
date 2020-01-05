package ascelion.kalah;

import java.io.File;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.interceptor.Interceptor;

import ascelion.config.api.ConfigValue;
import ascelion.kalah.shared.utils.SLF4JHandler;

import static java.util.Arrays.stream;

import io.helidon.microprofile.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class Main {
	static {
		if (Boolean.getBoolean("show.class.path")) {
			stream(System.getProperty("java.class.path")
					.split(File.pathSeparator))
							.sorted()
							.forEach(System.out::println);
		}

		SLF4JHandler.install();
	}

	static private final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		Server.create().start();
	}

	@ConfigValue("application.name")
	private String applicationName;

	void init(
	//@formatter:off
			@Observes
			@Initialized(ApplicationScoped.class)
			@Priority(Interceptor.Priority.APPLICATION - 100)
			Object unused) {
	//@formatter:on
		LOG.info("Starting {}", this.applicationName);
	}

	void done(
	//@formatter:off
			@Observes
			@Priority(Interceptor.Priority.APPLICATION - 100)
			@Destroyed(ApplicationScoped.class)
			Object unused) {
		//@formatter:on
		LOG.info("Finishing {}", this.applicationName);
	}
}
