package com.webflux.userservice.service.impl;

import com.webflux.userservice.dto.UserDto;
import com.webflux.userservice.entity.User;
import com.webflux.userservice.repository.UserRepository;
import com.webflux.userservice.service.UserService;
import com.webflux.userservice.utils.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> createOrUpdateUser(Mono<UserDto> userDtoMono, Integer id) {
        if (id != null) {
            //update User
            Mono<User> userOld = userRepository.findById(id);
            userOld.flatMap(
                    i -> {
                        return userDtoMono.map(
                                n -> {
                                    i.setName(n.getName());
                                    i.setBalance(n.getBalance());
                                    return i;
                                }
                        );
                    }
            ).flatMap(userRepository:: save);
            return userOld;
        }else {
            //create new User
            return userDtoMono
                    .map(EntityDtoUtil::toEntity)
                    .flatMap(this.userRepository::save);
        }
    }

    @Override
    public Mono<Void> deleteUser(int id) {
        return userRepository.deleteById(id);
    }
}
