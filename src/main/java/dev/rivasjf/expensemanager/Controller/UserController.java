package dev.rivasjf.expensemanager.Controller;

import dev.rivasjf.expensemanager.Common.Dto.ApiResponse;
import dev.rivasjf.expensemanager.Dto.Request.UserRegisterRequestDto;
import dev.rivasjf.expensemanager.Dto.Response.UserResponseDto;
import dev.rivasjf.expensemanager.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<UserResponseDto> register(@Valid @RequestBody UserRegisterRequestDto request) {
        return ApiResponse.success(this.userService.registerUser(request),"not implemented");
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDto> get(@PathVariable String id) {
        return ApiResponse.success(this.userService.getUserById(id),"not implemented");
    }

}
