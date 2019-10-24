package ascelion.kalah.boot;

import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.AmbiguousResolutionException;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import static java.util.Optional.ofNullable;

import io.undertow.servlet.api.ServletInfo;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

@Dependent
public class JerseyBean {
	@Inject
	private BeanManager bm;

	@Produces
	ServletInfo jersey() {
		final ServletInfo si = new ServletInfo("jersey", ServletContainer.class);

		si.setLoadOnStartup(1);
		si.addInitParam(ServerProperties.PROVIDER_PACKAGES, "ascelion");

		final Set<Bean<?>> beans = this.bm.getBeans(Application.class);

		if (beans.size() > 1) {
			throw new AmbiguousResolutionException("Too many applications");
		}
		if (beans.size() > 0) {
			final Bean<? extends Application> bean = (Bean<? extends Application>) beans.iterator().next();
			final Class<?> type = bean.getBeanClass();
			String path = ofNullable(type.getAnnotation(ApplicationPath.class))
					.map(ApplicationPath::value)
					.orElse(null);

			if (path != null) {
				if (!path.startsWith("/")) {
					path = "/" + path;
				}

				si.addMapping(path + "/*");
			}
		}

		if (si.getMappings().isEmpty()) {
			si.addMapping("/*");
		}

		return si;
	}
}
