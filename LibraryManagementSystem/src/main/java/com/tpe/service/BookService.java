package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.dto.BookDTO;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public void saveBook(Book book) {

        bookRepository.save(book);

    }

    public List<Book> findAllBooks() {

        return bookRepository.findAll();

    }

    public Book findBookById(Long id) {

        return bookRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Book not found with given id: "+id)
        );

    }

    public void deleteBookById(Long id) {

        // 1.
        Book book = findBookById(id);
        bookRepository.delete(book);

        // 2.
        /*
        findBookById(id);
        bookRepository.deleteById(id);
         */

    }

    public Book findBookByTitle(String title) {

        Book book = bookRepository.findBookByTitle(title);
        return book;

    }

    public Page<Book> findAllBooksWithPage(Pageable pageable) {

        return bookRepository.findAll(pageable);

    }


    public void updateBookWithDTO(Long id, BookDTO bookDTO) {

        Book bookFromDB = findBookById(id);

        // Update the fields of book from the DB
        bookFromDB.setTitle(bookDTO.getTitle());
        bookFromDB.setAuthor(bookDTO.getAuthor());
        bookFromDB.setPublicationDate(bookDTO.getPublicationDate());

        bookRepository.save(bookFromDB);

    }

    public List<Book> getBookByAuthorWithJPQL(String author) {

        List<Book> books = bookRepository.findByAuthorWithJPQL(author);

        if (books.isEmpty()){
            throw new ResourceNotFoundException("No books were found for the author: "+author);
        }

        return books;

    }
}
