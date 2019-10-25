package ascelion.kalah.shared.client;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.spi.RestClientBuilderListener;

public class RestClientConfigListener implements RestClientBuilderListener {

	@Override
	public void onNewBuilder(RestClientBuilder builder) {
		builder.register(RestClientExceptionMapper.class);
	}

}
