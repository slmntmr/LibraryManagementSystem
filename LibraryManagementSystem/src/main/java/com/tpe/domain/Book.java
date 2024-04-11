package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Book {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Kitap ismini gerekli format ile yazınız.")
    @NotNull(message = "Kitap ismi boş bırakılamaz.")
    @Size(min = 2, max = 25, message = "Kitap ismi '${validatedValue}', {min} ile {max} karakter arasında olmalı.")
    @Column(length = 25, nullable = false)
    private String title;

    private String author;

    @Column(nullable = false)
    private String publicationDate;

    @ManyToOne
    @JsonIgnore
    private Owner owner;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                '}';
    }
}