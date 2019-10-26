package ascelion.kalah.shared.client;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Provider
public class RestClientExceptionMapper implements ResponseExceptionMapper<RestClientException> {

	@Override
	public RestClientException toThrowable(Response response) {
		return new RestClientException(response);
	}

}
