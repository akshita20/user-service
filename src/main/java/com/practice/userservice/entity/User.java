package com.practice.userservice.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(value = "first name of the user", required = true)
    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @ApiModelProperty(value = "last name of the user", required = true)
    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @ApiModelProperty(value = "first name of the user", required = true)
    @NotBlank
    @Email
    @Column(name = "email_address")
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}