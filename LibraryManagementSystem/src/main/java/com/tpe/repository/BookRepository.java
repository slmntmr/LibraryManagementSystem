package com.tpe.repository;

import com.tpe.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    Book findBookByTitle(String title);

    @Query("SELECT b FROM Book b WHERE b.author = :yazar") // select * from book b where b.author=?
    List<Book> findByAuthorWithJPQL(@Param("yazar") String author);
}
