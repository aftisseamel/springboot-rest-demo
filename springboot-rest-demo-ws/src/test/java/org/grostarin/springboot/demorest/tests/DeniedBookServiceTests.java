package org.grostarin.springboot.demorest.tests;

import static org.assertj.core.api.Assertions.*;

import org.grostarin.springboot.demorest.domain.DeniedBooks;
import org.grostarin.springboot.demorest.services.DeniedBooksServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
public class DeniedBookServiceTests {
    @Autowired
    private DeniedBooksServices bookService;
    
    @Test
    public void testCreationNoAttributes() {
        DeniedBooks toCreate = new DeniedBooks();
        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy( () -> bookService.create(toCreate));
    }
}
