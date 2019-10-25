package ascelion.kalah.shared.endpoint.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ascelion.microprofile.config.ConfigValue;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
@ApplicationScoped
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	@ConfigValue("application.name")
	private String appName;

	public Response buildStatusResponse(Response.StatusType status, Object... content) {
		final Map<String, Object> values = new HashMap<>();

		values.put("timestamp", LocalDateTime.now());
		values.put("host", this.uriInfo.getBaseUri().getHost());
		values.put("method", this.request.getMethod());
		values.put("path", this.uriInfo.getRequestUri().getPath());
		values.put("source", this.appName);
		values.put("content", content);

		final MultivaluedMap<String, String> query = this.uriInfo.getQueryParameters();

		if (query.size() > 0) {
			values.put("query", query);
		}

		return Response
				.status(status)
				.entity(values)
				.type(APPLICATION_JSON)
				.build();
	}

	@Override
	public Response toResponse(Exception exception) {
		return buildStatusResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
	}
}
