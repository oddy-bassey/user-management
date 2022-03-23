package com.revoltcode.user.cmd.api.commands;

import com.revoltcode.user.core.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/*
 * NOTE:
 * In order for axon know which instance of the aggregate type should handle the command message,
 * the field carrying the aggregate identifier in the command object must be annotated with
 * @TargetAggregateIdentifier.
 *
 * Also remember that a command should carry the information required to undertake action based on
 * the expressed intent. E.g User information to register a new user
 */

@Data
@Builder
public class UpdateUserCommand {

    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull(message = "no user details were supplied")
    private User user;
}
