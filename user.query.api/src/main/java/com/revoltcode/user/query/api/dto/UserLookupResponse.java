package com.revoltcode.user.query.api.dto;

import com.revoltcode.user.core.dto.BaseResponse;
import com.revoltcode.user.core.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserLookupResponse extends BaseResponse {

    private List<User> users;

    public UserLookupResponse(String message) {
        super(message);
    }

    public UserLookupResponse(User user) {
        super(null);
        this.users = new ArrayList<>();
        users.add(user);
    }

    public UserLookupResponse(List<User> users) {
        super(null);
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
