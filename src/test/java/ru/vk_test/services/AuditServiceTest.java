package ru.vk_test.services;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vk_test.dao.AuditDao;
import ru.vk_test.dto.Audit;

import java.util.Map;

@SpringBootTest
public class AuditServiceTest {
    @Autowired
    AuditService auditService;
    @Test
    public void testCreate(){
        Audit audit = auditService.create(Map.of("id", new String[]{"1"}, "name", new String[]{"Audit"}),"admin");
        Assertions.assertEquals(audit.getUsername(), "admin");
    }
}
