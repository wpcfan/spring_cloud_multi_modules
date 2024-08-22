package com.fangxiaoer.authserver.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "uaa_client_auth_strategies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ClientAuthStrategyId.class)
public class ClientAuthStrategy {

    @Id
    @Column(name = "client_id", nullable = false, length = 255)
    private String clientId;

    @Id
    @Column(name = "strategy_name", nullable = false, length = 255)
    private String strategyName;

    // Add any additional fields if needed

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ClientAuthStrategy that = (ClientAuthStrategy) o;
        return getClientId() != null && Objects.equals(getClientId(), that.getClientId())
                && getStrategyName() != null && Objects.equals(getStrategyName(), that.getStrategyName());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(clientId, strategyName);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "clientId = " + clientId + ", " +
                "strategyName = " + strategyName + ")";
    }
}
