package com.webflux.productservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;
    private String description;
    private Integer price;

}
