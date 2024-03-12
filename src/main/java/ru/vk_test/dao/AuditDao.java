package ru.vk_test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vk_test.dto.Audit;
@Repository
public interface AuditDao extends JpaRepository<Audit, Long> {

}
