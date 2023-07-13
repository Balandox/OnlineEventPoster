package org.suai.courceWork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.models.enums.Category;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByTitleContainingIgnoreCase(String title);

    List<Product> findByTitleContainingIgnoreCaseAndCategory(String search, Category category);

    List<Product> findByOrderByTitle();

    @Query(value = "SELECT * FROM Product WHERE DATE(date) = ?1", nativeQuery = true)
    List<Product> findByDateOfEvent(Date date);


    List<Product> findByCategory(Category category);

    List<Product> findAllByTitle(String title);

    @Query(value = "SELECT * FROM Product ORDER BY rating DESC LIMIT 5", nativeQuery = true)
    List<Product> findTop5ByOrderByRatingDesc();


}
