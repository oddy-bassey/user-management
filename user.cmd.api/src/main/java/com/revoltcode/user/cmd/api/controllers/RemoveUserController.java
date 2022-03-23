package com.revoltcode.user.cmd.api.controllers;

import com.revoltcode.user.cmd.api.commands.RemoveUserCommand;
import com.revoltcode.user.cmd.api.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/removeUser")
public class RemoveUserController {

    private final CommandGateway commandGateway;

    public RemoveUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable(value = "id") String id){
        try{
            commandGateway.sendAndWait(new RemoveUserCommand(id));

            return new ResponseEntity<>(new BaseResponse("user successfully removed"), HttpStatus.OK);
        }catch (Exception e){
            var safeErrorMessage = "Error occurred while processing remove user request for id - "+id;
            e.printStackTrace();
            log.error(e.toString());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
