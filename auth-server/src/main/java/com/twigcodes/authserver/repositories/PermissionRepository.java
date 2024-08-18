package com.twigcodes.authserver.repositories;

import com.twigcodes.authserver.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findOptionalByAuthority(String authority);

    Set<Permission> findByIdIn(Set<Long> ids);

    @Query("select p from Permission p left join p.roles pr where pr.id <> ?1")
    Set<Permission> findAvailablePermissions(Long roleId);

    long countByAuthorityAndIdNot(String authority, Long Id);
}
