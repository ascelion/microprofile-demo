package ascelion.kalah.shared.endpoint;

import javax.inject.Inject;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import ascelion.kalah.shared.utils.SingletonBean;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
@SingletonBean
public class JacksonResolver implements ContextResolver<ObjectMapper> {
	@Inject
	private ObjectMapper om;

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return this.om;
	}
}
