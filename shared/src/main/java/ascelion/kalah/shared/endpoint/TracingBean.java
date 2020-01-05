package ascelion.kalah.shared.endpoint;

import javax.enterprise.context.Dependent;

import ascelion.config.api.ConfigPrefix;
import ascelion.config.api.ConfigValue;

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
