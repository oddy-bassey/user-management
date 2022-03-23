package com.revoltcode.user.cmd.api.controllers;

import com.revoltcode.user.cmd.api.commands.UpdateUserCommand;
import com.revoltcode.user.cmd.api.dto.BaseResponse;
import com.revoltcode.user.cmd.api.dto.RegisterUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/updateUser")
public class UpdateUserController {

    private final CommandGateway commandGateway;

    public UpdateUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable(value = "id") String id,
                                                   @RequestBody @Valid UpdateUserCommand command){
        try{
            command.setId(id);
            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new BaseResponse("user updated successfully"), HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "Error occurred while processing update user request for id - "+id;
            log.error(e.toString());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
























