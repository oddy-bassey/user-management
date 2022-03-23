package com.revoltcode.user.query.api.repository;

import com.revoltcode.user.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
