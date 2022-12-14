package org.suai.courceWork.models.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import org.suai.courceWork.models.enums.Category;
import org.suai.courceWork.validations.CategorySubset;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {

    @Size(min=5, message = "Не меньше 5 знаков")
    @NotBlank(message = "Укажите название!")
    private String title;

    @NotBlank(message = "Введите цену")
    @PositiveOrZero(message = "Цена не может быть отрицательной")
    private int price;

    @NotBlank(message = "Введите кол-во товара")
    @PositiveOrZero(message = "Кол-во товара не может быть отрицательным")
    private int amount;

    @NotNull
    private MultipartFile image;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    @NotNull(message = "Укажите дату мероприятия")
    @FutureOrPresent(message = "Укажите корректную дату проведения мероприятия")
    private Date dateOfEvent;

    //@CategorySubset(anyOf = {Category.CINEMA, Category.CONCERT, Category.SPORT, Category.THEATRE})
    private Category category;


}
