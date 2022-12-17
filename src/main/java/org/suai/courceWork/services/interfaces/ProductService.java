package org.suai.courceWork.services.interfaces;

import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.enums.Category;
import org.suai.courceWork.models.forms.ProductForm;

import java.util.Date;
import java.util.List;

public interface ProductService {

    Product getProductById(int id);

    List<Product> getAll();

    ProductForm getProductFormById(int id);

    void saveEditedProduct(ProductForm productForm, int productId, Date dateOfEvent);

    List<Product> searchProductByTitle(String search);

    List<Product> getAllByDate(String dateString);

    List<Product> getAllByCategory(Category category);

    List<Product> searchProductWithCategory(Category category, String search);
}
