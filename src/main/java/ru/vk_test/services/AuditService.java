package ru.vk_test.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.vk_test.dao.AuditDao;
import ru.vk_test.dto.Audit;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditDao auditDao;
    public Audit create(Map<String, String[]> parameters, String username){
        Audit audit = Audit.builder().createDate(new Date()).hasAccess(false)
                .parameters(parameters.toString())
                .username(username).build();
        return auditDao.save(audit);
    }
}
