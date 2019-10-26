package ascelion.kalah.shared.client;

import javax.enterprise.inject.spi.CDI;

import ascelion.kalah.shared.endpoint.JacksonFeature;
import ascelion.kalah.shared.endpoint.JacksonResolver;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.spi.RestClientListener;

public class RestClientConfigListener implements RestClientListener {

	@Override
	public void onNewClient(Class<?> serviceInterface, RestClientBuilder builder) {
		builder.register(RestClientExceptionMapper.class);
		builder.register(JacksonFeature.class);
		builder.register(CDI.current().select(JacksonResolver.class).get());
	}

}
