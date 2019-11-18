package ascelion.kalah.shared.config;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import ascelion.config.api.ConfigPrefix;
import ascelion.config.api.ConfigValue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Setter;

@ApplicationScoped
@ConfigPrefix("jackson")
public class JacksonConfig {
	private final ObjectMapper om = new ObjectMapper();

	@Setter(onParam_ = @ConfigValue(required = false))
	private boolean indent;

	@Produces
	public ObjectMapper objectMapper() {
		return this.om;
	}

	@PostConstruct
	private void postConstruct() {
		this.om.findAndRegisterModules();

		this.om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		this.om.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
		this.om.disable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);

		this.om.configure(SerializationFeature.INDENT_OUTPUT, this.indent);
	}
}
