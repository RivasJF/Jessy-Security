package dev.rivasjf.digitalbrain.Controller;

import dev.rivasjf.digitalbrain.Dto.Request.NoticeCreate;
import dev.rivasjf.digitalbrain.Dto.Request.NoticeUpdate;
import dev.rivasjf.digitalbrain.Dto.Response.NoticeResponse;
import dev.rivasjf.digitalbrain.Service.NoticeService;
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
    public ResponseEntity<NoticeResponse> registerNotice(@RequestBody NoticeCreate noticeCreate) {
        return ResponseEntity.ok(noticeService.save(noticeCreate));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        noticeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<NoticeResponse> updateNotice(@RequestBody NoticeUpdate noticeUpdate) {
        return ResponseEntity.ok(noticeService.update(noticeUpdate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> getNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<NoticeResponse>> getAllNotices() {
        return ResponseEntity.ok(noticeService.findAll());
    }

}
