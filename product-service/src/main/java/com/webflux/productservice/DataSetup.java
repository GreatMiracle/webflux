package com.webflux.productservice;

import com.webflux.productservice.dto.ProductDto;
import com.webflux.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class DataSetup implements CommandLineRunner {

    private final ProductService service;

    @Override
    public void run(String... args) throws Exception {
        ProductDto p1 = new ProductDto("4k-tv", 1000);
        ProductDto p2 = new ProductDto("slr-camera", 750);
        ProductDto p3 = new ProductDto("iphone", 800);
        ProductDto p4 = new ProductDto("headphone", 100);

        Flux.just(p1, p2, p3, p4)
                .concatWith(newProducts())
                .flatMap(p -> this.service.insertProduct(Mono.just(p)))
                .subscribe(System.out::println);

    }

    private Flux<ProductDto> newProducts(){
        return Flux.range(1, 1000)
                .delayElements(Duration.ofSeconds(2))
                .map(i -> new ProductDto("product-" + i, ThreadLocalRandom.current().nextInt(10, 100)));
    }
}

