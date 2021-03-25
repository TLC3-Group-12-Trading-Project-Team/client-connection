package com.example.client.connection.Admin;


import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Getter
@Service
@AllArgsConstructor
@ToString
@Setter
@NoArgsConstructor
@Table
@Entity(name = "admin")
public class Admin {
    @Id
    @SequenceGenerator(
            name = "client_sequence",
            sequenceName = "client_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_sequence"
    )
    @Column(
            nullable = false
    )
    private Long id;
    private String email;
    @Column(
            nullable = false
    )
    private String password;
    @Column(
            nullable = false
    )
    private String firstname;
    @Column(
            nullable = false
    )
    private String lastname;
}
