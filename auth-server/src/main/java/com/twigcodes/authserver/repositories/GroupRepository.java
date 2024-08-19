package com.twigcodes.authserver.repositories;

import com.twigcodes.authserver.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findOptionalByGroupName(String groupName);
    long countByGroupName(String groupName);
}
