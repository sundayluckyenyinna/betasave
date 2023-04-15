package com.pentazon.betasave.modules.user.repository;

import com.pentazon.betasave.modules.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRole, Long>
{
    UserRole findByRoleName(String roleName);
}
