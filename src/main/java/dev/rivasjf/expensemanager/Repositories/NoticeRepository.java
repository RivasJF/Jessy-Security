package dev.rivasjf.expensemanager.Repositories;

import dev.rivasjf.expensemanager.Entities.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
