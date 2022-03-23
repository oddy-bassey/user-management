package com.revoltcode.user.query.api.handlers;

import com.revoltcode.user.query.api.dto.UserLookupResponse;
import com.revoltcode.user.query.api.queries.FindAllUsersQuery;
import com.revoltcode.user.query.api.queries.FindUserByIdQuery;
import com.revoltcode.user.query.api.queries.SearchUserQuery;

public interface UserQueryHandler {

    UserLookupResponse getUserById(FindUserByIdQuery findUserByIdQuery);
    UserLookupResponse searchUsers(SearchUserQuery searchUserQuery);
    UserLookupResponse getAllUsers(FindAllUsersQuery findAllUsersQuery);
}
