package urmat.dev.megatask.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiCaller {

    private final WebClient webClient;

    @PostConstruct
    public void callExternalApi() {
        webClient.get()
                .uri("https://api.restful-api.dev/objects")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("External API Response: {}", response))
                .onErrorResume(error -> {
                    log.error("Error calling external API", error);
                    return Mono.empty();
                })
                .subscribe();
    }
}
