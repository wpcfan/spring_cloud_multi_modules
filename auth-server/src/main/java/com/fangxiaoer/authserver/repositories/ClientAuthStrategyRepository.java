package com.fangxiaoer.authserver.repositories;

import com.fangxiaoer.authserver.entities.ClientAuthStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAuthStrategyRepository extends JpaRepository<ClientAuthStrategy, Long> {

}
