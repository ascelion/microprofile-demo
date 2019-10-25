package ascelion.kalah.boot;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.servlet.ServletException;

import ascelion.microprofile.config.ConfigPrefix;
import ascelion.microprofile.config.ConfigValue;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainerInitializerInfo;
import io.undertow.servlet.api.ServletInfo;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ConfigPrefix("server")
@ApplicationScoped
public class UndertowBean {
	static private final Logger LOG = LoggerFactory.getLogger(UndertowBean.class);

	@Setter(onParam_ = @ConfigValue(required = false))
	private String host = "localhost";
	@Setter(onParam_ = @ConfigValue(required = false))
	private int port = 8080;
	@Setter(onParam_ = @ConfigValue(required = false))
	private String contextPath = "/";

	@ConfigValue("${application.name:-ROOT}")
	private String applicationName;

	private Undertow server;

	@Inject
	private Instance<ServletContainerInitializerInfo> scisInstance;

	@Inject
	private Instance<ServletInfo> sisInstance;

	void init(
	//@formatter:off
			@Observes
			@Initialized(ApplicationScoped.class)
			@Priority(Interceptor.Priority.APPLICATION)
			Object unused) throws ServletException {
	//@formatter:on
		LOG.info("Starting Undertow, host = {}, port = {}, path = {}", this.host, this.port, this.contextPath);

		final DeploymentInfo depi = Servlets.deployment()
				.setClassLoader(getClass().getClassLoader())
				.setDeploymentName(this.applicationName + ".war")
				.setContextPath(this.contextPath);

		this.scisInstance.forEach(depi::addServletContainerInitializer);
		this.sisInstance.forEach(depi::addServlet);

		final DeploymentManager depm = Servlets.defaultContainer().addDeployment(depi);

		depm.deploy();

		final PathHandler handler = Handlers.path()
				.addPrefixPath(this.contextPath, depm.start());

		this.server = Undertow.builder()
				.addHttpListener(this.port, this.host)
				.setHandler(handler)
				.build();

		this.server.start();
	}

	void done(
	//@formatter:off
			@Observes
			@Priority(Interceptor.Priority.APPLICATION)
			@Destroyed(ApplicationScoped.class)
			Object unused) {
	//@formatter:on
		LOG.info("Stopping Undertow,");

		this.server.stop();
	}
}
