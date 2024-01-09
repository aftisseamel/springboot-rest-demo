package org.grostarin.springboot.demorest.repositories;

import java.util.List;

import org.grostarin.springboot.demorest.domain.DeniedBooks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeniedBooksRepository extends JpaRepository<DeniedBooks, Long> {
    List<DeniedBooks> findByTitle(String title);
}
