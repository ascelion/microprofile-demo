package ascelion.kalah.shared.endpoint;

import javax.enterprise.context.Dependent;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import ascelion.microprofile.config.ConfigPrefix;
import ascelion.microprofile.config.ConfigValue;

import lombok.Setter;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;

@Provider
@Dependent
@ConfigPrefix("jersey.trace")
public class TraceFeature implements Feature {
	@Setter(onParam_ = @ConfigValue(required = false))
	private TracingConfig type = TracingConfig.OFF;
	@Setter(onParam_ = @ConfigValue(required = false))
	private String threshold = "TRACE";

	/*
	 * {@inheritDoc}
	 */
	@Override
	public boolean configure(FeatureContext context) {
		if (this.type != TracingConfig.OFF) {
			context.property(ServerProperties.TRACING, this.type);
			context.property(ServerProperties.TRACING_THRESHOLD, this.threshold);

			return true;
		} else {
			return false;
		}
	}
}
