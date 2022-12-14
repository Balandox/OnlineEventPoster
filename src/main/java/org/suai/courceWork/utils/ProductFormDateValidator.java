package org.suai.courceWork.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.models.forms.ProductForm;
import org.suai.courceWork.models.forms.UserForm;
import org.suai.courceWork.services.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

@Component
public class ProductFormDateValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductForm productForm = (ProductForm) target;

        StringTokenizer st = new StringTokenizer(productForm.getDateOfEvent(), "T");
        String date = st.nextToken();
        String time = st.nextToken();


        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy.MM.dd HH:mm");
        try {
            Date Date= format.parse(date + " " + time);
        } catch (ParseException e) {
            errors.rejectValue("dateOfEvent", "", "Правильный формат даты: yyyy.MM.dd HH:mm");
        }
    }

}
