package dev.rivasjf.digitalbrain.Repositories;

import dev.rivasjf.digitalbrain.Entities.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
