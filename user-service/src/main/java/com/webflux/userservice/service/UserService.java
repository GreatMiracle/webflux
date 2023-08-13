package com.webflux.userservice.service;

import com.webflux.userservice.dto.UserDto;
import com.webflux.userservice.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getAllUsers();

    Mono<User> getUserById(int id);

    Mono<User> createOrUpdateUser(Mono<UserDto> userDtoMono, Integer id);

    Mono<Void> deleteUser (int id);

}
