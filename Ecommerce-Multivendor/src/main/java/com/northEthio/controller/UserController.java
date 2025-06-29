package com.northEthio.controller;

import com.northEthio.model.User;
import com.northEthio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> creatUserHandler(
            @RequestHeader("authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);


        return ResponseEntity.ok(user);
    }
}
