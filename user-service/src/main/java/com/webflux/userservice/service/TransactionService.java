package com.webflux.userservice.service;

import com.webflux.userservice.dto.request.TransactionRequestDto;
import com.webflux.userservice.dto.response.TransactionResponseDto;
import com.webflux.userservice.entity.UserTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto);

    public Flux<UserTransaction> getByUserId(int userId);
}
