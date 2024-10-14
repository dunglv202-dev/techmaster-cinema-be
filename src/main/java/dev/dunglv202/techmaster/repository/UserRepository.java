package dev.dunglv202.techmaster.repository;

import dev.dunglv202.techmaster.entity.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
        FROM User u
        WHERE :str IS NOT NULL AND (u.phone = :str OR u.email = :str)
    """)
    Optional<User> findByPhoneOrEmail(@Nonnull @Param("str") String str);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
