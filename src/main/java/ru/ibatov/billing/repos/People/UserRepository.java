package ru.ibatov.billing.repos.People;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.People.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}