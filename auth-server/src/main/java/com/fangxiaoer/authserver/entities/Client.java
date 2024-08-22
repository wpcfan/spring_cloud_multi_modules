package com.fangxiaoer.authserver.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;


@Getter
@Setter
@Entity
@Table(name = "uaa_clients")
public class Client implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    @Column(length = 1000)
    private String clientAuthenticationMethods;
    @Column(length = 1000)
    private String authorizationGrantTypes;
    @Column(length = 1000)
    private String redirectUris;
    @Column(length = 1000)
    private String postLogoutRedirectUris;
    @Column(length = 1000)
    private String scopes;
    @Column(length = 2000)
    private String clientSettings;
    @Column(length = 2000)
    private String tokenSettings;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Client client = (Client) o;
        return getId() != null && Objects.equals(getId(), client.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "clientId = " + clientId + ", " +
                "clientIdIssuedAt = " + clientIdIssuedAt + ", " +
                "clientSecret = " + clientSecret + ", " +
                "clientSecretExpiresAt = " + clientSecretExpiresAt + ", " +
                "clientName = " + clientName + ", " +
                "clientAuthenticationMethods = " + clientAuthenticationMethods + ", " +
                "authorizationGrantTypes = " + authorizationGrantTypes + ", " +
                "redirectUris = " + redirectUris + ", " +
                "postLogoutRedirectUris = " + postLogoutRedirectUris + ", " +
                "scopes = " + scopes + ", " +
                "clientSettings = " + clientSettings + ", " +
                "tokenSettings = " + tokenSettings + ")";
    }
}
