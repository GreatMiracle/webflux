package com.webflux.productservice.service;

import com.webflux.productservice.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<ProductDto> getAll();
    Flux<ProductDto> getProductByPriceRange(int min, int max);
    Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono);
    Mono<ProductDto> getProductById(String id);
    Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono);
    Mono<Void> deleteProduct(String id);

}
