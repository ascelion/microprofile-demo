package ascelion.kalah.shared.endpoint;

import javax.enterprise.context.Dependent;

import ascelion.microprofile.config.ConfigPrefix;
import ascelion.microprofile.config.ConfigValue;

import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.message.internal.TracingLogger;
import org.glassfish.jersey.server.TracingConfig;

@Dependent
@ConfigPrefix("jersey.tracing")
@Setter(onParam_ = @ConfigValue(required = false))
@Getter
class TracingBean {
	private TracingConfig type = TracingConfig.OFF;
	private TracingLogger.Level threshold = TracingLogger.Level.TRACE;
}
