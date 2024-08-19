package com.twigcodes.authserver.repositories;

import com.twigcodes.authserver.entities.ClientAuthStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAuthStrategyRepository extends JpaRepository<ClientAuthStrategy, Long> {

}
