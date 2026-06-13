package dev.rivasjf.jessysecurity.user.controller;

import dev.rivasjf.jessysecurity.common.Dto.ApiResponse;
import dev.rivasjf.jessysecurity.user.dto.response.UserResponseDto;
import dev.rivasjf.jessysecurity.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDto> get(@PathVariable String id) {
        return ApiResponse.success(this.userService.getUserById(id),"not implemented");
    }

}
