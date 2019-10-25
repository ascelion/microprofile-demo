package ascelion.kalah.shared.client;

import javax.ws.rs.core.Response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RestClientException extends RuntimeException {
	private final Response response;
}
