package ascelion.kalah.shared.endpoint.exception;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
@ApplicationScoped
public class RuntimeMapper implements ExceptionMapper<RuntimeException> {
	@Inject
	private GenericMapper gem;

	@Override
	public Response toResponse(RuntimeException exception) {
		return this.gem.buildStatusResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
	}
}
