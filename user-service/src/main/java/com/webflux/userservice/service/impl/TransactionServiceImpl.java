package com.webflux.userservice.service.impl;

import com.webflux.userservice.dto.enums.TransactionStatus;
import com.webflux.userservice.dto.request.TransactionRequestDto;
import com.webflux.userservice.dto.response.TransactionResponseDto;
import com.webflux.userservice.entity.UserTransaction;
import com.webflux.userservice.repository.UserRepository;
import com.webflux.userservice.repository.UserTransactionRepository;
import com.webflux.userservice.service.TransactionService;
import com.webflux.userservice.utils.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final UserTransactionRepository transactionRepository;

    @Override
    public Mono<TransactionResponseDto> createTransaction(TransactionRequestDto requestDto) {
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDtoUtil.toEntity(requestDto))
                .flatMap(this.transactionRepository::save)
                .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    @Override
    public Flux<UserTransaction> getByUserId(int userId) {
        return this.transactionRepository.findByUserId(userId);
    }
}
