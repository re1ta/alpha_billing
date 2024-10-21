package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}