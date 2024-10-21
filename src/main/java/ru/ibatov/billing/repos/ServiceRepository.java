package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}