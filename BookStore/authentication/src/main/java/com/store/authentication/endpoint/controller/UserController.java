package com.store.authentication.endpoint.controller;

import com.store.core.model.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.HttpStatus.OK;

/**
 * Rest controller for the user.
 *
 * @author rafaelcolombodesouza
 */
@RestController
@RequestMapping(value = "user")
@Api(value = "Endpoints to manage user data")
public class UserController {

    @GetMapping(path = "info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Will return the information from the user available in the token.", response = UserEntity.class)
    public ResponseEntity<UserEntity> getUserInfo(Principal principal) {
        UserEntity userEntity = (UserEntity) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(userEntity, OK);
    }
}
