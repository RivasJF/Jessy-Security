package dev.rivasjf.digitalbrain.Entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NoticeTest {

    @Test
    void create() {
        Notice notice = Notice.create("message");
        assertEquals("message", notice.getMessage());
    }
}