package ascelion.kalah.shared.endpoint;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Provider
public class JacksonFeature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		context.register(JacksonJaxbJsonProvider.class);

		return true;
	}

}
