package ascelion.kalah.shared.endpoint.exception;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class WebApplicationMapper implements ExceptionMapper<WebApplicationException> {
	@Inject
	private GenericMapper gem;

	@Override
	public Response toResponse(WebApplicationException exception) {
		return this.gem.buildStatusResponse(exception.getResponse().getStatusInfo(), exception.getMessage());
	}

}
