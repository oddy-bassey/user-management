package com.revoltcode.user.query.api.handlers;

import com.revoltcode.user.core.events.UserRegisteredEvent;
import com.revoltcode.user.core.events.UserRemovedEvent;
import com.revoltcode.user.core.events.UserUpdatedEvent;
import com.revoltcode.user.query.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*
 * A ProcessingGroup is similar to a ConsumerGroup which basically means:
 * when a consumer or event handler(in our case) consumes an event, axon will track the offset
 * to make sure that within a given processing group, you always consume the latest event.
 *
 * NB: Axon basically manages the consumed offset for each ProcessingGroup separately
 *
 * EventHandler basically define the business logic to performed when the event is
 * received or consumed from the EventBus
 */
@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository userRepository;

    @Autowired
    public UserEventHandlerImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @EventHandler
    @Override
    public void on(UserRegisteredEvent event) {
        userRepository.save(event.getUser());
    }

    @EventHandler
    @Override
    public void on(UserUpdatedEvent event) {
        userRepository.save(event.getUser());
    }

    @EventHandler
    @Override
    public void on(UserRemovedEvent event) {
        userRepository.deleteById(event.getId());
    }
}
