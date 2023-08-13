package com.webflux.userservice.repository;

import com.webflux.userservice.entity.UserTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransaction, Integer > {
    Flux<UserTransaction> findByUserId(int userId);
}
