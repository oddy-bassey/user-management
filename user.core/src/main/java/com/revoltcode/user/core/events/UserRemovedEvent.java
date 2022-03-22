package com.revoltcode.user.core.events;

import com.revoltcode.user.core.models.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserRemovedEvent {

    private String id;
}
