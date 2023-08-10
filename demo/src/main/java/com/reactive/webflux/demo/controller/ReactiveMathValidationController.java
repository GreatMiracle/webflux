package com.reactive.webflux.demo.controller;

import com.reactive.webflux.demo.dto.ResponseDto;
import com.reactive.webflux.demo.exception.InputValidationException;
import com.reactive.webflux.demo.service.ReactiveMathService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {
     ReactiveMathService mathService;

    @GetMapping("square/{input}/throw")
    public Mono<ResponseDto> findSquare(@PathVariable int input){
        if(input < 10 || input > 20)
            throw new InputValidationException(input);
        return mathService.findSquare(input);
    }

    @GetMapping("square/{input}/mono-error")
    public Mono<ResponseDto> monoErr (@PathVariable int input){
        return Mono.just(input)
                .handle((integer, responseDtoSynchronousSink) -> {
                    if (integer >= 10 && integer <= 20) {
                        responseDtoSynchronousSink.next(integer);
                    }else {
                        responseDtoSynchronousSink.error(new InputValidationException(integer));
                    }
                })
                .cast(Integer.class)
                .flatMap(mathService::findSquare);
    }

    @GetMapping("square/{input}/assignment")
    public Mono<ResponseEntity<ResponseDto>> assignment(@PathVariable int input){
        return Mono.just(input)
                .filter(i -> i>=10 && i<= 20 )
                .flatMap(mathService::findSquare)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
