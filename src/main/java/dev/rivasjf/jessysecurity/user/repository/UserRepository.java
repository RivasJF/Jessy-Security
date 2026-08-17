package dev.rivasjf.jessysecurity.user.repository;

import dev.rivasjf.jessysecurity.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByEmail(String email);
     Optional<User> findByPublicId(UUID publicId);
     Boolean existsByEmail(String email);
}
