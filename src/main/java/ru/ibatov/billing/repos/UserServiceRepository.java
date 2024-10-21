package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.UserService;

@Repository
public interface UserServiceRepository extends JpaRepository<UserService, Long> {
}