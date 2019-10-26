package ascelion.kalah.players;

import ascelion.kalah.shared.endpoint.ViewEntityEndpoint;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "endpoints.players.")
@Retry(maxRetries = 5)
public interface PlayersEndpoint extends ViewEntityEndpoint<Player> {
}
