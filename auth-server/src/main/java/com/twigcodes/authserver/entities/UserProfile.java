package com.twigcodes.authserver.entities;

import com.twigcodes.authserver.utils.Constants;
import com.twigcodes.authserver.validators.email.ValidEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_profiles")
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String name;

    @NotNull
    @Pattern(regexp = Constants.PATTERN_MOBILE)
    @Size(min = 11, max = 11)
    @Column(length = 11, unique = true, nullable = false)
    private String mobile;

    @ValidEmail
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true, nullable = false)
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        UserProfile userProfile = (UserProfile) o;
        return id != null && Objects.equals(id, userProfile.id);
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "mobile = " + mobile + ", " +
                "email = " + email + ")";
    }
}
