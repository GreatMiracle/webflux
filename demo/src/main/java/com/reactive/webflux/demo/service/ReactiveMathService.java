package com.reactive.webflux.demo.service;

import com.reactive.webflux.demo.dto.MultiplyRequestDto;
import com.reactive.webflux.demo.dto.ResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReactiveMathService {
    public Mono<ResponseDto> findSquare(int input){
        return Mono.fromSupplier(() -> input * input).map(ResponseDto::new);
    }

    public Flux<ResponseDto> multiplicationTable(int input){
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("math-service processing : " + i))
                .map(i -> new ResponseDto(i * input));
    }

//    public Flux<ResponseDto> multiplicationTable(int input){
//
//        List<ResponseDto> responseDtoList = IntStream.rangeClosed(1, 10)
//                .peek(i -> {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                })
//                .peek(i -> System.out.println("math-service processing : " + i))
//                .mapToObj(i -> new ResponseDto(i * input))
//                .collect(Collectors.toList());
//        return Flux.fromIterable(responseDtoList);
//    }


    public Mono<ResponseDto> multiply (Mono<MultiplyRequestDto> dtoMono){
        return dtoMono.map(
                i -> i.getFirst() * i.getSecond()
        ).map(ResponseDto::new);
    }

}
