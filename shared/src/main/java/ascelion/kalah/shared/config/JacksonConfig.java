package ascelion.kalah.shared.config;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import ascelion.microprofile.config.ConfigValue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@ApplicationScoped
public class JacksonConfig {
	private final ObjectMapper om = new ObjectMapper();

	@ConfigValue("${jackson.indent:-false}")
	private boolean indent;

	@Produces
	public ObjectMapper objectMapper() {
		return this.om;
	}

	@PostConstruct
	private void postConstruct() {
		this.om.findAndRegisterModules();
		this.om.configure(SerializationFeature.INDENT_OUTPUT, this.indent);
	}
}
