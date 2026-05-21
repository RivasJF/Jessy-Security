package dev.rivasjf.digitalbrain.Service;

import dev.rivasjf.digitalbrain.Dto.Request.NoticeCreate;
import dev.rivasjf.digitalbrain.Dto.Request.NoticeUpdate;
import dev.rivasjf.digitalbrain.Dto.Response.NoticeResponse;
import dev.rivasjf.digitalbrain.Entities.Notice;
import dev.rivasjf.digitalbrain.Mapper.NoticeMapper;
import dev.rivasjf.digitalbrain.Repositories.NoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public NoticeResponse save(NoticeCreate noticeCreate) {
        Notice notice = Notice.create(noticeCreate.getMessage());
        noticeRepository.save(notice);
        return NoticeMapper.toDto(notice);
    }
    public NoticeResponse findById(Long id) {
        Notice notice = noticeRepository.findById(id).orElse(null);
        if (notice == null) {
            throw new EntityNotFoundException("Not found");
        }
        return NoticeMapper.toDto(notice);
    }
    public List<NoticeResponse> findAll() {
        List<Notice> listNotice = noticeRepository.findAll();
        return NoticeMapper.toDtoList(listNotice);
    }

    public NoticeResponse update(NoticeUpdate noticeUpdate) {
        Notice notice = noticeRepository.findById(noticeUpdate.getId()).orElse(null);
        if (notice == null) {
            throw new EntityNotFoundException("Not found");
        }
        notice.updateMessage(noticeUpdate.getMessage());
        noticeRepository.save(notice);
        return NoticeMapper.toDto(notice);
    }

    public void deleteById(Long id) {
        boolean exists = noticeRepository.existsById(id);
        if (exists) {
            noticeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Not found");
        }
    }
}
