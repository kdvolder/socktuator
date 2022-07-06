package socktuator.rsocket;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import socktuator.config.RSocktuatorServerProps;
import socktuator.dto.Request;

public class RSocktuatorClient {
	
	private RSocketRequester requestor;
	
	public RSocktuatorClient(RSocktuatorServerProps serverProps) {
		RSocketRequester.Builder builder = RSocketRequester.builder();
        requestor = builder
          .rsocketConnector(
             rSocketConnector ->
               rSocketConnector.reconnect(Retry.fixedDelay(5, Duration.ofSeconds(5)))
          )
          .rsocketStrategies(sb -> {
        	  sb.decoder(new Jackson2JsonDecoder());
        	  sb.encoder(new Jackson2JsonEncoder());
          })
          .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
          .tcp(serverProps.getHost(), serverProps.getPort());
	}

	
	public Mono<Object> health() {
		return call("health.health", Map.of());
	}
	
	public Mono<Object> healthForPath(String... path) {
		return call("health.healthForPath", Map.of("path", path));
	}
	
	public Mono<Object> call(String operationId, Map<String, Object> params) {
		return requestor
			.route(RSocktuatorRoutes.ACTUATOR)
			.data(new Request(operationId, params))
			.retrieveMono(Object.class);
	}

}