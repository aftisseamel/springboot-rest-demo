package org.grostarin.springboot.demorest.services;

import java.util.LinkedList;
import java.util.List;

import org.grostarin.springboot.demorest.domain.Book;
import org.grostarin.springboot.demorest.domain.DeniedBooks;
import org.grostarin.springboot.demorest.dto.BookSearch;
import org.grostarin.springboot.demorest.exceptions.BookIdMismatchException;
import org.grostarin.springboot.demorest.exceptions.BookNotFoundException;
import org.grostarin.springboot.demorest.repositories.BookRepository;
import org.grostarin.springboot.demorest.repositories.DeniedBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BookServices {    

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DeniedBooksRepository deniedBookRepository;
    
    public Iterable<Book> findAll(BookSearch bookSearchDTO) {
        if(bookSearchDTO!=null && StringUtils.hasText(bookSearchDTO.title())) {
            return bookRepository.findByTitle(bookSearchDTO.title());  
        }
        return bookRepository.findAll();
    }

    public Book findOne(long id) {
        return bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
    }

    public boolean create(Book book) {
        
        if(!isDenied(book))
        {
            bookRepository.save(book);
        return true;
        }
        return false;
    }


    // isDenied: fonction qui prend en parametre un livre, et qui verifie si il existe ou pas dans la liste de livres bannis definit statiquement
    public boolean isDenied(Book book)
    {
        List <DeniedBooks> deniedBooks = new LinkedList<>();
        deniedBooks = deniedBookRepository.findAll();

        for(DeniedBooks bookIterate : deniedBooks)
        {
            if(book.getTitle().equals(bookIterate.getTitle()) && book.getAuthor().equals(bookIterate.getAuthor()))
            {
                return true;
            }
        }

        return false;
    }

    public void delete(long id) {
        bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    public Book updateBook(Book book, long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
}
