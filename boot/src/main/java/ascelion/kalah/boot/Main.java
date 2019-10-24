package ascelion.kalah.boot;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.interceptor.Interceptor;

import ascelion.kalah.shared.utils.SLF4JHandler;
import ascelion.microprofile.config.ConfigValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class Main {
	static {
//		stream(System.getProperty("java.class.path")
//				.split(File.pathSeparator))
//						.sorted()
//						.forEach(System.out::println);
		SLF4JHandler.install();
	}

	static private final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws InterruptedException {
		final SeContainerInitializer init = SeContainerInitializer.newInstance();

		try (SeContainer container = init.initialize()) {
			container.select(Main.class)
					.get()
					.run();
		}
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

	void run() throws InterruptedException {
		Thread.currentThread().join();
	}
}