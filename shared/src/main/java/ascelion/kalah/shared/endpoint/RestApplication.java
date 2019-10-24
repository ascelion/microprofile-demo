package ascelion.kalah.shared.endpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("/rest")
public class RestApplication extends Application {
}
