package ascelion.kalah.players;

import ascelion.kalah.shared.ViewEndpoint;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "endpoints.players.")
public interface PlayersEndpoint extends ViewEndpoint<Player> {
}
