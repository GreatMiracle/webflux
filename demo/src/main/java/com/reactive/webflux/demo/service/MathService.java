package com.reactive.webflux.demo.service;


import com.reactive.webflux.demo.dto.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MathService {

    public ResponseDto findSquare(int input){
        return new ResponseDto(input * input);
    }

    public List<ResponseDto> multiplicationTable(int input){
        return IntStream.rangeClosed(1, 10)
                .peek(i -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .peek(i -> System.out.println("math-service processing : " + i))
                .mapToObj(i -> new ResponseDto(i * input))
                .collect(Collectors.toList());
    }
}
