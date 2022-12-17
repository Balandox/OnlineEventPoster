package org.suai.courceWork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.models.enums.Category;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

/*    List<Product> findByTitleIgnoreCaseStartingWith(String title);*/

    List<Product> findByTitleContainingIgnoreCase(String title);

    List<Product> findByTitleContainingIgnoreCaseAndCategory(String search, Category category);
    List<Product> findByOrderByTitle();

    List<Product> findAllByCategory(Category category);

    List<Product> findAllByTitle(String title);


}
