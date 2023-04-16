package com.pentazon.betasave.modules.user.repository;

import com.pentazon.betasave.modules.user.model.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserPermissionRepository extends JpaRepository<UserPermission, Long>
{
    @Query("SELECT t FROM UserPermission t where t.roleId = :roleId")
    List<UserPermission> findAllPermissionsForRole(@Param("roleId") Long roleId);
}
