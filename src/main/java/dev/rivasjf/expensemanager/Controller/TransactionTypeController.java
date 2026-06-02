package dev.rivasjf.expensemanager.Controller;

import dev.rivasjf.expensemanager.Common.Dto.ApiResponse;
import dev.rivasjf.expensemanager.Dto.Request.TransactionTypeRegisterRequestDto;
import dev.rivasjf.expensemanager.Dto.Response.TransactionTypeResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction-type")
public class TransactionTypeController {

    @PostMapping("/register")
    public ApiResponse<TransactionTypeResponseDto> registerTransactionType(@RequestBody TransactionTypeRegisterRequestDto  request) {
        return ApiResponse.success(null, "Not implemented yet");
    }

    @GetMapping()
    public ApiResponse<TransactionTypeResponseDto> getAllTransactionType() {
        return ApiResponse.success(null, "Not implemented yet");
    }
}
