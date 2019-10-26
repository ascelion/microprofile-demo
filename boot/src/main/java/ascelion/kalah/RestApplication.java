package ascelion.kalah;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import lombok.Getter;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationScoped
@ApplicationPath("/rest")
public class RestApplication extends Application {
	@Getter
	private final Map<String, Object> properties = new HashMap<>();

	@PostConstruct
	private void postConstruct() {
		this.properties.put(ServerProperties.PROVIDER_PACKAGES, getClass().getPackage().getName());
		this.properties.put(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);
	}
}
