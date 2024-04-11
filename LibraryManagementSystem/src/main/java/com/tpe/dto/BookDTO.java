package com.tpe.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDTO {

    @NotBlank(message = "Kitap ismini gerekli format ile yazınız.")
    @NotNull(message = "Kitap ismi boş bırakılamaz.")
    @Size(min = 2, max = 25, message = "Kitap ismi '${validatedValue}', {min} ile {max} karakter arasında olmalı.")
    private String title;

    private String author;

    private String publicationDate;

}
