package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.domain.Owner;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.BookRepository;
import com.tpe.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;


    public void saveOwner(Owner owner) {

        boolean emailExists = ownerRepository.existsByEmail(owner.getEmail());
        if (emailExists){
            throw new ConflictException("The given email already exists in the database.");
        }

        ownerRepository.save(owner);

    }

    public List<Owner> getAllOwners() {

        //return ownerRepository.findAll();

        List<Owner> owners = ownerRepository.findAll();
        if (owners.isEmpty())
            throw new ResourceNotFoundException("No owner found.");

        return owners;

    }

    public Owner findOwnerByID(Long id) {

        return ownerRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("No owner was found with given ID: "+id)
        );

    }

    public void addBookToOwner(Long ownerID, Long bookID) {

        // OwnerID ile Owner'ı bul
        Owner owner = findOwnerByID(ownerID);

        // BookID ile Book'u bul
        Book book = bookService.findBookById(bookID);

        // Belirtilen Book, daha önce bu Owner'a kaydedildi mi?
        //if (owner.getBooks().contains(book))
        boolean bookExists = owner.getBooks()
                .stream() // Bir akışın (stream) içerisine aldık
                .anyMatch( // Eşleşme var mı?
                        bookInOwner-> bookInOwner.getId().equals(bookID)
                );

        if (bookExists){
            throw new ConflictException("Book already exists for the specified Owner with ID: "+ownerID);
        }

        // Owner'a kitabı ekle
        owner.getBooks().add(book);
        book.setOwner(owner);

        ownerRepository.save(owner);
        bookRepository.save(book);


    }
}
