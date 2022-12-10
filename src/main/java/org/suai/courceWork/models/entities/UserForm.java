package org.suai.courceWork.models.entities;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

    @Size(min=5, message = "Не меньше 5 знаков")
    @NotBlank(message = "Укажите логин!")
    private String name;

    @Size(min=5, message = "Не меньше 5 знаков")
    @NotBlank(message = "Укажите пароль!")
    private String password;

    @NotBlank(message = "Подтвердите пароль!")
    private String passwordConfirm;

    private String newPassword;

    @Email(message = "Некорректный адрес электронной почты!")
    @NotBlank(message = "Укажите почту!")
    private String email;

}
