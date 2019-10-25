package ascelion.kalah.shared.endpoint;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import lombok.Getter;
import org.glassfish.jersey.server.ServerProperties;

@Dependent
@ApplicationPath("/rest")
public class RestApplication extends Application {
	@Inject
	private TracingBean tracing;
	@Getter
	private final Map<String, Object> properties = new HashMap<>();

	@PostConstruct
	private void postConstruct() {
		this.properties.put(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);
		this.properties.put(ServerProperties.TRACING, this.tracing.getType().name());
		this.properties.put(ServerProperties.TRACING_THRESHOLD, this.tracing.getThreshold().name());
	}
}
