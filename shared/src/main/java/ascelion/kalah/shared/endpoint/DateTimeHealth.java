package ascelion.kalah.shared.endpoint;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class DateTimeHealth implements HealthCheck {

	private final AtomicLong readyTime = new AtomicLong();

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse
				.named(getClass().getSimpleName())
				.state(isReady())
				.withData("ready", this.readyTime.get())
				.withData("time", System.currentTimeMillis())
				.build();
	}

	void onStartUp(@Observes @Initialized(ApplicationScoped.class) Object init) {
		this.readyTime.set(System.currentTimeMillis());
	}

	private boolean isReady() {
		return Duration.ofMillis(System.currentTimeMillis() - this.readyTime.get()).getSeconds() >= 5;
	}
}
