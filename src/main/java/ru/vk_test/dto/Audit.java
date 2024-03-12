package ru.vk_test.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.StreamingHttpOutputMessage;
import ru.vk_test.security.Account;

import java.util.Date;
@Entity
@Table(name = "audit")
@Data
@AllArgsConstructor
@Builder
public class Audit {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column(nullable = false, name = "create_date")
    private Date createDate;
    @Column
    private String parameters;
    @Column(nullable = false, name = "has_access")
    private Boolean hasAccess;

    public Audit() {

    }
}
