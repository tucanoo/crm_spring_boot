package com.tucanoo.crm.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "appuser")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String role;

    @NotBlank
    String username;

    @NotBlank
    String fullName;

    @Column(nullable = false, length = 64)
    String password;

    Boolean enabled = true;

    @CreationTimestamp
    private LocalDateTime dateCreated;

}
