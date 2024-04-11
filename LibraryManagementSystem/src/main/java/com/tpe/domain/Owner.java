package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // Parameterized Constructor
@ToString
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Ismizi gerekli format ile yazınız.")
    @NotNull(message = "Ismiziz boş bırakılamaz.")
    @Size(min = 2, max = 25, message = "Ismiziz '${validatedValue}', {min} ile {max} karakter arasında olmalı.")
    @Column(length = 25, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String lastName;

    private String phoneNumber;

    @Email(message = "Lütfen düzgün bir mail bilgisi veriniz.") // @, ...com
    @Column(nullable = false, unique = true)
    private String email;

    @Setter(AccessLevel.NONE)
    private LocalDateTime registrationDate = LocalDateTime.now();

    @OneToMany(mappedBy = "owner")
    private List<Book> books = new ArrayList<>();

}
