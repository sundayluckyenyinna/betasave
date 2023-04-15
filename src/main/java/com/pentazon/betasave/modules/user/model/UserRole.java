package com.pentazon.betasave.modules.user.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_role")
public class UserRole
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", unique = true)
    private String roleName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
