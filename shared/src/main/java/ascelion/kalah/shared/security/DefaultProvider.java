package ascelion.kalah.shared.security;

import java.util.concurrent.CompletionStage;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import ascelion.config.api.ConfigValue;

import io.helidon.security.AuthenticationResponse;
import io.helidon.security.AuthorizationResponse;
import io.helidon.security.EndpointConfig;
import io.helidon.security.OutboundSecurityResponse;
import io.helidon.security.ProviderRequest;
import io.helidon.security.SecurityEnvironment;
import io.helidon.security.SubjectType;
import io.helidon.security.providers.httpauth.HttpBasicAuthProvider;
import io.helidon.security.providers.httpauth.SecureUserStore;
import io.helidon.security.spi.AuthenticationProvider;
import io.helidon.security.spi.AuthorizationProvider;
import io.helidon.security.spi.OutboundSecurityProvider;

@Dependent
class DefaultProvider implements AuthenticationProvider, AuthorizationProvider, OutboundSecurityProvider {

	@ConfigValue(value = "application.name", usePrefix = false)
	private String applicationName;

	@Inject
	private SecureUserStore users;

	private HttpBasicAuthProvider auth;

	@Override
	public CompletionStage<AuthenticationResponse> authenticate(ProviderRequest request) {
		return this.auth.authenticate(request);
	}

	@Override
	public CompletionStage<AuthorizationResponse> authorize(ProviderRequest request) {
		return this.auth.authorize(request);
	}

	@Override
	public CompletionStage<OutboundSecurityResponse> outboundSecurity(ProviderRequest providerRequest, SecurityEnvironment env, EndpointConfig config) {
		return this.auth.outboundSecurity(providerRequest, env, config);
	}

	@PostConstruct
	private void build() {
		this.auth = HttpBasicAuthProvider.builder()
				.realm(this.applicationName)
				.subjectType(SubjectType.USER)
				.userStore(this.users)
				.build();
	}
}
