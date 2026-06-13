package dev.rivasjf.expensemanager.user.controller;

import dev.rivasjf.expensemanager.common.Dto.ApiResponse;
import dev.rivasjf.expensemanager.user.dto.response.UserResponseDto;
import dev.rivasjf.expensemanager.user.service.UserService;
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
