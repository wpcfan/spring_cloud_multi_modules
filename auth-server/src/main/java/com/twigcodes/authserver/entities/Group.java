package com.twigcodes.authserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "group_name", unique = true, nullable = false, length = 50)
    private String groupName;
    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;
    @Builder.Default
    @JsonIgnore
    @ManyToMany(mappedBy = "groups")
    private Set<Role> roles = new HashSet<>();
    @Builder.Default
    @JsonIgnore
    @ManyToMany(mappedBy = "groups")
    private Set<User> users = new HashSet<>();
}