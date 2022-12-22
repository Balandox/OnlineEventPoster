package org.suai.courceWork.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.models.forms.UserForm;
import org.suai.courceWork.services.implementations.UserServiceImpl;
import org.suai.courceWork.services.interfaces.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserForm userForm = (UserForm) target;
        if(this.userService.findFirstByName(userForm.getName()) != null)
            errors.rejectValue("name", "" , "Пользователь с таким именем уже существует!");

        if(!userForm.getPassword().equals(userForm.getPasswordConfirm()))
            errors.rejectValue("passwordConfirm", "", "Пароли не совпадают!");

        if(this.userService.findByEmail(userForm.getEmail()) != null)
            errors.rejectValue("email", "", "Пользователь с таким email уже существует!");
    }

}
