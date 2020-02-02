package ascelion.kalah.boards;

import ascelion.kalah.shared.ViewEntityEndpoint;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "endpoints.boards.")
public interface BoardsEndpoint extends ViewEntityEndpoint<Board> {
}
