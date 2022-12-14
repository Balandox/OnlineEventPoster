package org.suai.courceWork.validations;

import org.suai.courceWork.models.enums.Category;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CategorySubsetValidator implements ConstraintValidator<CategorySubset,Category> {
    private Category[] subset;

    @Override
    public void initialize(CategorySubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(Category value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}