package com.revoltcode.user.cmd.api.controllers;

import com.revoltcode.user.cmd.api.commands.RegisterUserCommand;
import com.revoltcode.user.cmd.api.dto.RegisterUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/*
 * The CommandGateway is a command dispatching mechanism that will dispatch the register user command
 * and once dispatched, it will invoke the command handling constructor for the register user command
 * and command handling methods for the other user commands
 */

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/registerUser")
public class RegisterUserController {

    private final CommandGateway commandGateway;

    @Autowired
    public RegisterUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid RegisterUserCommand command){
        String id = UUID.randomUUID().toString();
        command.setId(id);
        try{
            /*
             * sendsWait(object) will only respond when the command handler has completed it's work (register user)
             * send(object) will immediately respond and assume that the user was registered
             */
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new RegisterUserResponse(id,"User successfully registered"), HttpStatus.CREATED);

        }catch (Exception e){
            var safeErrorMessage = "Error occurred while processing register user request for id - "+id;
            log.error(e.toString());

            return new ResponseEntity<>(new RegisterUserResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

























