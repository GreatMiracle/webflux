package com.webflux.productservice.service.Impl;

import com.webflux.productservice.dto.ProductDto;
import com.webflux.productservice.entity.Product;
import com.webflux.productservice.repository.ProductRepository;
import com.webflux.productservice.service.ProductService;
import com.webflux.productservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private  final ProductRepository productRepository;

    @Override
    public Flux<ProductDto> getAll() {
        Flux<Product> productFlux = productRepository.findAll();
        return productFlux.map(EntityDtoUtil::toDto);
    }

    @Override
    public Flux<ProductDto> getProductByPriceRange(int min, int max) {
        return productRepository.findByPriceBetween(Range.<Integer>from(Range.Bound.inclusive(min)).to(Range.Bound.inclusive(max))).map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<ProductDto> getProductById(String id) {
        return productRepository.findById(id).map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return productRepository.findById(id)
                .flatMap(p -> productDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }


}
