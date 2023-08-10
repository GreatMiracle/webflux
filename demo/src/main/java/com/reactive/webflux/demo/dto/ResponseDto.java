package com.reactive.webflux.demo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDto {
     Date date = new Date();
     int output;

    public ResponseDto(int output) {
        this.output = output;
    }
}
