package ascelion.kalah.shared.endpoint.exception;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ascelion.kalah.shared.client.RestClientException;

@Provider
@ApplicationScoped
public class RestClientExceptionMapper implements ExceptionMapper<RestClientException> {
	@Inject
	private GenericExceptionMapper gem;

	@Override
	public Response toResponse(RestClientException exception) {
		final Response response = exception.getResponse();

		return response.hasEntity()
				? this.gem.buildStatusResponse(response.getStatusInfo(), response.readEntity(Map.class))
				: this.gem.buildStatusResponse(response.getStatusInfo());
	}

}
