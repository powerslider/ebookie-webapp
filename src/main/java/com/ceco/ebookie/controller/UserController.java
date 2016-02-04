package com.ceco.ebookie.controller;

import com.ceco.ebookie.model.User;
import com.ceco.ebookie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 09-Jan-2016
 */
@RequestMapping("/api/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/authenticate",
            method = { RequestMethod.POST }
    )
    public ResponseEntity<Integer> authenticateUser(@RequestParam(value = "username", required = true) String username,
                                              @RequestParam(value = "password", required = true) String password) {
        Integer userId = userService
                .isUserAuthenticated(username, password);
        if (userId != Integer.MAX_VALUE) {
            return new ResponseEntity<>(userId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(
            value = "/{username}",
            method = { RequestMethod.GET },
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> getUserData(@PathVariable String username) {
        User searchedUser = userService
                .getAvailableUser(username);

        if (searchedUser == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(searchedUser, HttpStatus.OK);
        }
    }

    @RequestMapping(
            value = "/register",
            method = { RequestMethod.POST }
    )
    public ResponseEntity<String> registerUser(@RequestBody User newUser) {
        Boolean isUserCreated = userService
                .createNewUser(newUser);
        if (isUserCreated) {
            return new ResponseEntity<>(
                    "Successfully registered new user '" + newUser.getUsername() + "'", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Error while registering new user '" + newUser.getUsername() + "'", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
