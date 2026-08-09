package dev.rivasjf.jessysecurity.account.repository;

import dev.rivasjf.jessysecurity.account.entitie.Account;
import dev.rivasjf.jessysecurity.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(Long userId);
    Optional<Account> findByUserAndPublicId(User user, UUID publicId);
    Optional<Account> deleteByUserAndPublicId(User user, UUID publicId);
}
