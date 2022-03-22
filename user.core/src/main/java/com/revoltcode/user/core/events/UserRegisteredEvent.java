package com.revoltcode.user.core.events;

import com.revoltcode.user.core.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisteredEvent {

    private String id;
    private User user;
}
