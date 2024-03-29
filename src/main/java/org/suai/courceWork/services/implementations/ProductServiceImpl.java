package org.suai.courceWork.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.enums.Category;
import org.suai.courceWork.models.forms.ProductForm;
import org.suai.courceWork.repositories.ProductRepository;
import org.suai.courceWork.services.interfaces.ProductService;
import org.suai.courceWork.utils.DateParser;

import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(int id){

        Optional<Product> product = this.productRepository.findById(id);
        return product.orElse(null);

    }

    public List<Product> getAll(){
        return productRepository.findByOrderByTitle();
    }

    public ProductForm getProductFormById(int id){
        Optional<Product> product = this.productRepository.findById(id);

        if(product.isPresent())
            return new ProductForm(product.get());

        return null;
    }

    public void saveEditedProduct(ProductForm productForm, int productId, Date dateOfEvent){
        Product product = this.getProductById(productId);

        if(product != null) {
            product.setTitle(productForm.getTitle());
            product.setPrice(productForm.getPrice());
            product.setAmount(productForm.getAmount());
            product.setImgName(productForm.getImageName());
            product.setDateOfEvent(dateOfEvent);
            product.setCategory(productForm.getCategory());
            product.setRating(productForm.getRating());
        }
        else{
            product = new Product();
            product.setTitle(productForm.getTitle());
            product.setPrice(productForm.getPrice());
            product.setAmount(productForm.getAmount());
            product.setImgName(productForm.getImageName());
            product.setDateOfEvent(dateOfEvent);
            product.setCategory(productForm.getCategory());
            product.setRating(productForm.getRating());
            this.productRepository.save(product);
        }

    }

    public List<Product> searchProductByTitle(String search){
        return this.productRepository.findByTitleContainingIgnoreCase(search);
    }

    public List<Product> getAllByDate(String dateString){
        Date date = DateParser.getDate(dateString);
        return this.productRepository.findByDateOfEvent(date);
    }

    public List<Product> getAllByCategory(Category category){
        return productRepository.findByCategory(category);
    }

    public List<Product> searchProductWithCategory(Category category, String search){
        return this.productRepository.findByTitleContainingIgnoreCaseAndCategory(search, category);
    }

    @Override
    public List<Product> getTopFiveEvents() {
        return this.productRepository.findTop5ByOrderByRatingDesc();
    }


}
