package com.tpe.controller;

import com.tpe.domain.Book;
import com.tpe.dto.BookDTO;
import com.tpe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // Body
//@Controller // Model ve View
@RequestMapping("/books") // http://localhost:8080/books / ...com/books
public class BookController {

    @Autowired
    private BookService bookService;

    // Save a Book
    @PostMapping // http://localhost:8080/books + POST
    public ResponseEntity<String> saveBook(@Valid @RequestBody Book book){

        bookService.saveBook(book);

        return new ResponseEntity<>("Kitap başarıyla kaydedildi.", HttpStatus.CREATED); // 201

    }

    // Get All Books
    @GetMapping // http://localhost:8080/books + GET
    public ResponseEntity<List<Book>> getAllBooks(){

        List<Book> books = bookService.findAllBooks();

        return ResponseEntity.ok(books); // 200

    }

    // Get a Book by its ID
    @GetMapping("/{id}") // http://localhost:8080/books/2 + GET
    public ResponseEntity<Book> getBookByIdWithPathVariable(@PathVariable("id") Long id){

        Book book = bookService.findBookById(id);

        return ResponseEntity.ok(book);

    }

    // Delete a Book by its ID
    @DeleteMapping("/{id}") // http://localhost:8080/books/2 + DELETE
    public ResponseEntity<String> deleteBook(@PathVariable Long id){

        bookService.deleteBookById(id);

        return ResponseEntity.ok("Book with ID: "+id+" has been deleted successfully.");

    }

    // Get a Book by its ID with RequestParam
    @GetMapping("/q") // http://localhost:8080/books/q?id=2
    public ResponseEntity<Book> getBookByIdWithRequestParam(@RequestParam("id") Long id){

        Book book = bookService.findBookById(id);

        return ResponseEntity.ok(book); // 200

    }

    // Get a Book by its Title with RequestParam
    @GetMapping("/search") //http://localhost:8080/books/search?title=Atomic Habits
    public ResponseEntity<Book> getBookByTitleWithRequestParam(@RequestParam("title") String title){

        Book book=bookService.findBookByTitle(title);
        return ResponseEntity.ok(book); //2

    }

    // Get Books With Page
    @GetMapping("/s") // http://localhost:8080/books/s?page=1&size=2&sort=publicationDate&direction=ASC
    public ResponseEntity<Page<Book>> getBooksWithPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sortBy, // prop
            @RequestParam("direction") Sort.Direction direction
            ){

        Pageable pageable = PageRequest.of(page-1, size, Sort.by(direction, sortBy));

        Page<Book> bookPage = bookService.findAllBooksWithPage(pageable);

        return ResponseEntity.ok(bookPage); // 200

    }

    // Update a Book With Using DTO
    @PutMapping("/update/{id}") // http://localhost:8080/books/update/2
    public ResponseEntity<Map<String, String>> updateBookWithDTO(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO){

        bookService.updateBookWithDTO(id, bookDTO);

        Map<String, String> msg = new HashMap<>();
        msg.put("message", "Book has been updated successfully.");

        return ResponseEntity.ok(msg);

    }

    // Get a Book By Its Author Using JPQL
    @GetMapping("/a") // http://localhost:8080/books/a?author=AB
    public ResponseEntity<List<Book>> getBookByAuthorWithJPQL(@RequestParam("author") String author){

        List<Book> books = bookService.getBookByAuthorWithJPQL(author);

        return ResponseEntity.ok(books);

    }


}
