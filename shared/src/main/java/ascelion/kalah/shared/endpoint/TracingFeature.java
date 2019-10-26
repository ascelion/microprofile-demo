package ascelion.kalah.shared.endpoint;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ServerProperties;

@Dependent
@Provider
public class TracingFeature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		final TracingBean tracing = CDI.current().select(TracingBean.class).get();

		context.property(ServerProperties.TRACING, tracing.getType().name());
		context.property(ServerProperties.TRACING_THRESHOLD, tracing.getThreshold().name());

		return true;
	}

}
