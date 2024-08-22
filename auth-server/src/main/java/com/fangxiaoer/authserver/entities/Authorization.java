package com.fangxiaoer.authserver.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@Entity
@Table(name = "uaa_authorizations")
public class Authorization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private String id;
    private String registeredClientId;
    private String principalName;
    private String authorizationGrantType;
    @Column(length = 1000)
    private String authorizedScopes;
    @Column(length = 4000)
    private String attributes;
    @Column(length = 500)
    private String state;

    @Column(length = 4000)
    private String authorizationCodeValue;
    private Instant authorizationCodeIssuedAt;
    private Instant authorizationCodeExpiresAt;
    private String authorizationCodeMetadata;

    @Column(length = 4000)
    private String accessTokenValue;
    private Instant accessTokenIssuedAt;
    private Instant accessTokenExpiresAt;
    @Column(length = 2000)
    private String accessTokenMetadata;
    private String accessTokenType;
    @Column(length = 1000)
    private String accessTokenScopes;

    @Column(length = 4000)
    private String refreshTokenValue;
    private Instant refreshTokenIssuedAt;
    private Instant refreshTokenExpiresAt;
    @Column(length = 2000)
    private String refreshTokenMetadata;

    @Column(length = 4000)
    private String oidcIdTokenValue;
    private Instant oidcIdTokenIssuedAt;
    private Instant oidcIdTokenExpiresAt;
    @Column(length = 2000)
    private String oidcIdTokenMetadata;
    @Column(length = 2000)
    private String oidcIdTokenClaims;

    @Column(length = 4000)
    private String userCodeValue;
    private Instant userCodeIssuedAt;
    private Instant userCodeExpiresAt;
    @Column(length = 2000)
    private String userCodeMetadata;

    @Column(length = 4000)
    private String deviceCodeValue;
    private Instant deviceCodeIssuedAt;
    private Instant deviceCodeExpiresAt;
    @Column(length = 2000)
    private String deviceCodeMetadata;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Authorization that = (Authorization) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "registeredClientId = " + registeredClientId + ", " +
                "principalName = " + principalName + ", " +
                "authorizationGrantType = " + authorizationGrantType + ", " +
                "authorizedScopes = " + authorizedScopes + ", " +
                "attributes = " + attributes + ", " +
                "state = " + state + ", " +
                "authorizationCodeValue = " + authorizationCodeValue + ", " +
                "authorizationCodeIssuedAt = " + authorizationCodeIssuedAt + ", " +
                "authorizationCodeExpiresAt = " + authorizationCodeExpiresAt + ", " +
                "authorizationCodeMetadata = " + authorizationCodeMetadata + ", " +
                "accessTokenValue = " + accessTokenValue + ", " +
                "accessTokenIssuedAt = " + accessTokenIssuedAt + ", " +
                "accessTokenExpiresAt = " + accessTokenExpiresAt + ", " +
                "accessTokenMetadata = " + accessTokenMetadata + ", " +
                "accessTokenType = " + accessTokenType + ", " +
                "accessTokenScopes = " + accessTokenScopes + ", " +
                "refreshTokenValue = " + refreshTokenValue + ", " +
                "refreshTokenIssuedAt = " + refreshTokenIssuedAt + ", " +
                "refreshTokenExpiresAt = " + refreshTokenExpiresAt + ", " +
                "refreshTokenMetadata = " + refreshTokenMetadata + ", " +
                "oidcIdTokenValue = " + oidcIdTokenValue + ", " +
                "oidcIdTokenIssuedAt = " + oidcIdTokenIssuedAt + ", " +
                "oidcIdTokenExpiresAt = " + oidcIdTokenExpiresAt + ", " +
                "oidcIdTokenMetadata = " + oidcIdTokenMetadata + ", " +
                "oidcIdTokenClaims = " + oidcIdTokenClaims + ", " +
                "userCodeValue = " + userCodeValue + ", " +
                "userCodeIssuedAt = " + userCodeIssuedAt + ", " +
                "userCodeExpiresAt = " + userCodeExpiresAt + ", " +
                "userCodeMetadata = " + userCodeMetadata + ", " +
                "deviceCodeValue = " + deviceCodeValue + ", " +
                "deviceCodeIssuedAt = " + deviceCodeIssuedAt + ", " +
                "deviceCodeExpiresAt = " + deviceCodeExpiresAt + ", " +
                "deviceCodeMetadata = " + deviceCodeMetadata + ")";
    }
}
