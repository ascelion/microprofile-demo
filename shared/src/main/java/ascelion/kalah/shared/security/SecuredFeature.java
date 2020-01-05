package ascelion.kalah.shared.security;

import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import io.helidon.security.Security;
import io.helidon.security.integration.jersey.SecurityFeature;
import io.helidon.security.spi.SecurityProvider;

@Provider
public class SecuredFeature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		context.register(buildFeature());

		return true;
	}

	private SecurityFeature buildFeature() {
		return SecurityFeature.builder(buildSecurity())
				.build();
	}

	private Security buildSecurity() {
		return Security.builder()
				.addProvider(buildProvider())
				.build();
	}

	private SecurityProvider buildProvider() {
		return CDI.current().select(DefaultProvider.class).get();
	}
}
