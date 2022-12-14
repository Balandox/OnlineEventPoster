package org.suai.courceWork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.models.enums.Category;
import org.suai.courceWork.models.forms.ProductForm;
import org.suai.courceWork.repositories.ProductRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
        return productRepository.findByOrderByTitle();
    }

    public List<Product> getAllByCategory(Category category){return productRepository.findAllByCategory(category);}

    public ProductForm getProductFormById(int id){
        Optional<Product> product = this.productRepository.findById(id);

        if(product.isPresent())
            return new ProductForm(product.get());

        return null;
    }

    public void saveEditedProduct(ProductForm productForm, int productId, Date dateOfEvent){
        Product product = this.getProductById(productId);

        product.setTitle(productForm.getTitle());
        product.setPrice(productForm.getPrice());
        product.setAmount(productForm.getAmount());
        product.setImgName(productForm.getImageName());
        product.setDateOfEvent(dateOfEvent);
        product.setCategory(productForm.getCategory());

    }

    public void deleteProductById(int id){
        this.productRepository.deleteById(id);
    }

}
