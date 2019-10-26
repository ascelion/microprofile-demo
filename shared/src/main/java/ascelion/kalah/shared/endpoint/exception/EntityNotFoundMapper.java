package ascelion.kalah.shared.endpoint.exception;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
@ApplicationScoped
public class EntityNotFoundMapper implements ExceptionMapper<EntityNotFoundException> {
	@Inject
	private GenericMapper gem;

	@Override
	public Response toResponse(EntityNotFoundException exception) {
		return this.gem.buildStatusResponse(NOT_FOUND, exception.getMessage());
	}
}
