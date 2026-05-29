package dev.rivasjf.expensemanager.Mapper;

import dev.rivasjf.expensemanager.Dto.Response.NoticeResponse;
import dev.rivasjf.expensemanager.Entities.Notice;

import java.util.List;

public class NoticeMapper {

    public static NoticeResponse toDto(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .message(notice.getMessage())
                .createdAt(notice.getCreatedAtUTC())
                .build();
    }

    public static List<NoticeResponse> toDtoList(List<Notice> notices) {
        return notices.stream().map(NoticeMapper::toDto).toList();
    }
}
