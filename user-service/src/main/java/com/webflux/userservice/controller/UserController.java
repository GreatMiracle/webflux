package com.webflux.userservice.controller;

import com.webflux.userservice.dto.UserDto;
import com.webflux.userservice.entity.User;
import com.webflux.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("all")
    public Flux<User> all(){
        return this.service.getAllUsers();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable int id){
        return this.service.getUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable int id, @RequestBody Mono<UserDto> userDtoMono){
        return this.service.createOrUpdateUser(userDtoMono, id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteUser(@PathVariable int id){
        return this.service.deleteUser(id);
    }

}