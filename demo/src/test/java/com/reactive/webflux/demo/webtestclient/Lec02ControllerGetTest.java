package com.reactive.webflux.demo.webtestclient;

import com.reactive.webflux.demo.controller.ParamsController;
import com.reactive.webflux.demo.controller.ReactiveMathController;
import com.reactive.webflux.demo.dto.ResponseDto;
import com.reactive.webflux.demo.service.ReactiveMathService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@WebFluxTest(controllers = {ReactiveMathController.class, ParamsController.class})
public class Lec02ControllerGetTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService reactiveMathService;

    @Test
    public void singleResponseTest(){

        Mockito.when(reactiveMathService.findSquare(Mockito.anyInt())).thenReturn(Mono.empty());

        this.client
                .get()
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResponseDto.class)
                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(-1));

    }

    @Test
    public void listResponseTest(){

        Flux<ResponseDto> flux = Flux.range(1, 3)
                .map(ResponseDto::new);

        Mockito.when(reactiveMathService.multiplicationTable(Mockito.anyInt())).thenReturn(flux);

        this.client
                .get()
                .uri("/reactive-math/table/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ResponseDto.class)
                .hasSize(3);

    }

    @Test
    public void streamingResponseTest(){

        Flux<ResponseDto> flux = Flux.range(1, 3)
                .map(ResponseDto::new)
                .delayElements(Duration.ofMillis(100));

        Mockito.when(reactiveMathService.multiplicationTable(Mockito.anyInt())).thenReturn(flux);

        this.client
                .get()
                .uri("/reactive-math/table/{number}/stream", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .expectBodyList(ResponseDto.class)
                .hasSize(3);

    }

    @Test
    public void paramsTest(){

        Map<String, Integer> map = Map.of(
                "count", 10,
                "page", 20
        );

        this.client
                .get()
                .uri(b -> b.path("/jobs/search").query("count={count}&page={page}").build(map))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(2).contains(10, 20);
    }

}
