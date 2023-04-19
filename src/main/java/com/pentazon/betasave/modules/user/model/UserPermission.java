package com.pentazon.betasave.modules.user.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_permission")
public class UserPermission
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
