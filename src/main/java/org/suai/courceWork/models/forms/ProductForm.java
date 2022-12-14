package org.suai.courceWork.models.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.enums.Category;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {

    int id;
    @Size(min=5, message = "Не меньше 5 знаков")
    @NotBlank(message = "Укажите название!")
    private String title;

    @Positive(message = "Цена не может быть больше нуля")
    @NotNull(message = "Укажите цену товара")
    private Integer price;

    @PositiveOrZero(message = "Кол-во товара не может быть отрицательным")
    @NotNull(message = "Укажите цену товара")
    private Integer amount;

    @NotBlank(message = "Введите имя картинки с расширением")
    private String imageName;

    private String dateOfEvent;

    //@CategorySubset(anyOf = {Category.CINEMA, Category.CONCERT, Category.SPORT, Category.THEATRE})
    private Category category;

    public ProductForm(Product product){
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.amount = product.getAmount();
        this.imageName = product.getImgName();
        this.dateOfEvent = product.getDateOfEvent().toString();
        this.category = product.getCategory();
    }

    public Category[] getCategoryArray(){
        return Category.values();
    }

}
