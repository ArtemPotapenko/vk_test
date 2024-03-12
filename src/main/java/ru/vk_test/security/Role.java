package ru.vk_test.security;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
public class Role implements GrantedAuthority {

    public final static String ADMIN = "ADMIN";
    public final static String ALBUMS_EDITOR = "ALBUMS_EDITOR";
    public final static String ALBUMS_VIEWER = "ALBUMS_VIEWER";
    public final static String POSTS_EDITOR = "POSTS_EDITOR";
    public final static String POSTS_VIEWER = "POSTS_VIEWER";
    public final static String USERS_EDITOR = "USERS_EDITOR";
    public final static String USERS_VIEWER = "USERS_VIEWER";

    @Id
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "account_role",
     joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "account_id"))
    private Set<Account> users;

    public Role() {

    }

    @Override
    public String getAuthority() {
        return null;
    }

}

