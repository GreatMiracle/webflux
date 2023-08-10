package com.reactive.webflux.demo.controller;

import com.reactive.webflux.demo.dto.ResponseDto;
import com.reactive.webflux.demo.service.MathService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MathController {
     MathService mathService;

    @GetMapping("square/{input}")
    public ResponseDto findSquare(@PathVariable int input) {
        if (input < 10)
            throw new IllegalArgumentException();
        return mathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public List<ResponseDto> multiplicationTable(@PathVariable int input){
        return mathService.multiplicationTable(input);
    }
}
