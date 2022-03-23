package com.revoltcode.user.query.api.handlers;

import com.revoltcode.user.query.api.dto.UserLookupResponse;
import com.revoltcode.user.query.api.queries.FindAllUsersQuery;
import com.revoltcode.user.query.api.queries.FindUserByIdQuery;
import com.revoltcode.user.query.api.queries.SearchUserQuery;
import com.revoltcode.user.query.api.repository.UserRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserQueryHandlerImpl implements UserQueryHandler {

    private final UserRepository userRepository;

    @Autowired
    public UserQueryHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryHandler
    @Override
    public UserLookupResponse getUserById(FindUserByIdQuery findUserByIdQuery) {
        var user = userRepository.findById(findUserByIdQuery.getId());
        return user.isPresent()? new UserLookupResponse(user.get()) : null;
    }

    @QueryHandler
    @Override
    public UserLookupResponse searchUsers(SearchUserQuery searchUserQuery) {
        var users = new ArrayList<>(userRepository.findByFilterRegex(searchUserQuery.getFilter()));
        return new UserLookupResponse(users);
    }

    @QueryHandler
    @Override
    public UserLookupResponse getAllUsers(FindAllUsersQuery findAllUsersQuery) {
        var users = new ArrayList<>(userRepository.findAll());
        return new UserLookupResponse(users);
    }
}
