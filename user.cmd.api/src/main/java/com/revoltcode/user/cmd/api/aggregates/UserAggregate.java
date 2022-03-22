package com.revoltcode.user.cmd.api.aggregates;

import com.revoltcode.user.cmd.api.commands.RegisterUserCommand;
import com.revoltcode.user.cmd.api.commands.RemoveUserCommand;
import com.revoltcode.user.cmd.api.commands.UpdateUserCommand;
import com.revoltcode.user.cmd.api.security.PasswordEncoder;
import com.revoltcode.user.cmd.api.security.PasswordEncoderImpl;
import com.revoltcode.user.core.events.UserRegisteredEvent;
import com.revoltcode.user.core.events.UserRemovedEvent;
import com.revoltcode.user.core.events.UserUpdatedEvent;
import com.revoltcode.user.core.models.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

/*
 * @Aggregate : enables axon framework know this is the aggregate for the user command API
 *
 * @AggregateIdentifier : marks the id field as the reference point to the user aggregate object that axon uses to know
 * which aggregate that a given command is targeting. Remember, we map the id's of the command
 * objects with @TargetAggregateIdentifier
 *
 * @CommandHandler: tells axon that this is a Command Handler constructor
 *
 * @EventSourcingHandler: tells the axon framework that the annotated function should be called when the aggregate is
 * sourced from it's events.. as all the event handlers combined will form the aggregate. this is where all the state
 * changes are going to happen
 *
 */

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private User user;

    private final PasswordEncoder passwordEncoder;

    public UserAggregate(){
        this.passwordEncoder = new PasswordEncoderImpl();
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand command, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;

        var newUser = command.getUser();
        newUser.setId(command.getId());
        var password = newUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashPassword(password);
        newUser.getAccount().setPassword(hashedPassword);

        var event = UserRegisteredEvent.builder()
                .id(command.getId())
                .user(newUser)
                .build();

        AggregateLifecycle.apply(event); // stores the event in the EventStore and publishes it into the EventBus
    }

    @CommandHandler
    public void handle(UpdateUserCommand command){
        var updatedUser = command.getUser();
        updatedUser.setId(command.getId());
        var password = updatedUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashPassword(password);
        updatedUser.getAccount().setPassword(hashedPassword);

        var event = UserUpdatedEvent.builder()
                .id(command.getId())
                .user(updatedUser)
                .build();

        AggregateLifecycle.apply(event); // stores the event in the EventStore and publishes it into the EventBus
    }

    @CommandHandler
    public void handle(RemoveUserCommand command){
        var event = new UserRemovedEvent();
        event.setId(command.getId());
        AggregateLifecycle.apply(event); // stores the event in the EventStore and publishes it into the EventBus
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent event){
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent event){
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent event){
        AggregateLifecycle.markDeleted();
    }
}


























