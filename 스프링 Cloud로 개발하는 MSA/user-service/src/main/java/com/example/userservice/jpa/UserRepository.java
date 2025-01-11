package com.example.userservice.jpa;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserId(final String userId);

    UserEntity findByEmail(String email);
}
