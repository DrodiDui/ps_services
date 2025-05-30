package com.kapitonau.authserver.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatusModel {

    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_statuses_status_id_seq")
    @SequenceGenerator(name = "user_statuses_status_id_seq", sequenceName = "user_statuses_status_id_seq", allocationSize = 1)
    private Long statusId;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;
}
