package com.twigcodes.authserver.repositories;

import com.twigcodes.authserver.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findOptionalByRoleName(String roleName);
    Set<Role> findByIdIn(Set<Long> ids);
    long countByRoleNameIgnoreCase(String roleName);
    long countByRoleNameIgnoreCaseAndIdNot(String roleName, Long Id);
}
