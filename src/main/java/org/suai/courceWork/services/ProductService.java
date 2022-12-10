package org.suai.courceWork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.models.enums.Category;
import org.suai.courceWork.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(int id){

        Optional<Product> product = this.productRepository.findById(id);
        return product.orElse(null);

    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public List<Product> getAllByCategory(Category category){return productRepository.findAllByCategory(category);}
}
