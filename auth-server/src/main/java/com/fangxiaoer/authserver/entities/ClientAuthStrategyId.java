package com.fangxiaoer.authserver.entities;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientAuthStrategyId implements Serializable {

    private String clientId;
    private String strategyName;

    // Override equals and hashCode methods for composite key comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientAuthStrategyId that = (ClientAuthStrategyId) o;
        return clientId.equals(that.clientId) &&
                strategyName.equals(that.strategyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, strategyName);
    }
}
