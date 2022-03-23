package com.revoltcode.user.query.api.handlers;

import com.revoltcode.user.core.events.UserRegisteredEvent;
import com.revoltcode.user.core.events.UserRemovedEvent;
import com.revoltcode.user.core.events.UserUpdatedEvent;

public interface UserEventHandler {

    public  void on(UserRegisteredEvent event);
    public  void on(UserUpdatedEvent event);
    public  void on(UserRemovedEvent event);
}
