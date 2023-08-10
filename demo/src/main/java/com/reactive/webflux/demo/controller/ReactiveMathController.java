package com.reactive.webflux.demo.controller;

import com.reactive.webflux.demo.dto.MultiplyRequestDto;
import com.reactive.webflux.demo.dto.ResponseDto;
import com.reactive.webflux.demo.service.ReactiveMathService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("reactive-math")
public class ReactiveMathController {

     ReactiveMathService mathService;

    @GetMapping("square/{input}")
    public Mono<ResponseDto> findSquare(@PathVariable int input){
        return mathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<ResponseDto> multiplicationTable(@PathVariable int input){
        return mathService.multiplicationTable(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseDto> multiplicationTableStream(@PathVariable int input) {
        return this.mathService.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<ResponseDto> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono,
                                   @RequestHeader Map<String, String> headers){
        System.out.println(headers);
        return mathService.multiply(requestDtoMono);
    }


}
