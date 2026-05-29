package dev.rivasjf.expensemanager.Entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoticeTest {

    @Test
    void create() {
        Notice notice = Notice.create("message");
        assertEquals("message", notice.getMessage());
    }
}