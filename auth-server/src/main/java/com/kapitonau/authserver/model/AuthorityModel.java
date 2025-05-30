package com.kapitonau.authserver.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorityModel {

    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "authorities_authority_id_seq")
    @SequenceGenerator(name = "authorities_authority_id_seq", sequenceName = "authorities_authority_id_seq", allocationSize = 1)
    private Long authorityId;

    @Column(name = "authority_code")
    private String authorityCode;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    private List<RoleModel> roles;

}
