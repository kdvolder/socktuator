package socktuator.api;

import java.util.Map;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.util.MimeType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import socktuator.dto.OperationMetadata;
import socktuator.dto.SharedObjectMapper;

public interface SocktuatorClient {

	Mono<Object> call(String operationId, Map<String, Object> params);
	Flux<DataBuffer> callForBytes(String operationId, MimeType textPlain, Map<String, Object> params);
	
	default Mono<OperationMetadata[]> getEndpointMetadata() {
		return call("actuator.actuator", Map.of())
		.map(untyped_resp -> SharedObjectMapper.get().convertValue(untyped_resp, OperationMetadata[].class));
	}

	default Mono<Object> health() {
		return call("health.health", Map.of());
	}
	
	default Mono<Object> healthForPath(String... path) {
		return call("health.healthForPath", Map.of("path", path));
	}
}
