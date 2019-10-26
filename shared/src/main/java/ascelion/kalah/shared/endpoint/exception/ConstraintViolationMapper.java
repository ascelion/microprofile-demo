package ascelion.kalah.shared.endpoint.exception;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static java.util.Collections.singletonMap;

@Provider
@ApplicationScoped
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

	static private String nameOf(Path path) {
		final String str = path.toString();
		final int dot = str.lastIndexOf('.');

		return dot < 0 ? str : str.substring(dot + 1);
	}

	@Inject
	private GenericMapper gem;

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		return this.gem.buildStatusResponse(Response.Status.BAD_REQUEST, exception.getConstraintViolations().stream()
				.map(v -> singletonMap(nameOf(v.getPropertyPath()), v.getMessage()))
				.toArray());
	}

}
