package dev.rivasjf.expensemanager.Controller;

import dev.rivasjf.expensemanager.Common.Dto.ApiResponse;
import dev.rivasjf.expensemanager.Dto.Request.NoticeCreate;
import dev.rivasjf.expensemanager.Dto.Request.NoticeUpdate;
import dev.rivasjf.expensemanager.Dto.Response.NoticeResponse;
import dev.rivasjf.expensemanager.Service.NoticeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<NoticeResponse>> registerNotice(@Valid @RequestBody NoticeCreate noticeCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(noticeService.save(noticeCreate), "Notice created"));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<Void>> deleteNotice(@PathVariable Long id) {
        noticeService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Notice deleted"));
    }

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse<NoticeResponse>> updateNotice(@Valid @RequestBody NoticeUpdate noticeUpdate) {
        return ResponseEntity.ok(ApiResponse.success(noticeService.update(noticeUpdate), "Notice updated"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoticeResponse>> getNotice(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(noticeService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NoticeResponse>>> getAllNotices() {
        return ResponseEntity.ok(ApiResponse.success(noticeService.findAll()));
    }

}
