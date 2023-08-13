package com.webflux.productservice.controller;

import com.webflux.productservice.dto.ProductDto;
import com.webflux.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("all")
    public Flux<ProductDto> getAll(){
        return productService.getAll();
    }

    @PostMapping("add-new-product")
    public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDtoMono){
        return productService.insertProduct(productDtoMono);
    }

    @GetMapping("{id}")
    public Mono<ProductDto> getProductById(@PathVariable String id){
        return productService.getProductById(id);
    }

    @GetMapping("price-range")
    public Flux<ProductDto> getByPriceRange(@RequestParam("min") int min,
                                            @RequestParam("max") int max){
        return productService.getProductByPriceRange(min, max);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> productDtoMono){
        return productService.updateProduct(id, productDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
        return productService.deleteProduct(id);
    }

}
