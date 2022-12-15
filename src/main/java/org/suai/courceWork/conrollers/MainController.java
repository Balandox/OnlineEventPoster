package org.suai.courceWork.conrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.models.forms.UserForm;
import org.suai.courceWork.models.enums.Category;
import org.suai.courceWork.services.ProductService;
import org.suai.courceWork.services.UserService;
import org.suai.courceWork.utils.UserValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

    private final ProductService productService;
    private final UserService userService;

    private final UserValidator userValidator;

    @Autowired
    public MainController(ProductService productService, UserService userService, UserValidator userValidator) {
        this.productService = productService;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping({"", "/"})
    public String index(Model model){
        List<Product> list = this.productService.getAll();
        model.addAttribute("products", list);
        return "main/index";
    }

    @GetMapping("/catalog")
    public String getProductByCategory(@RequestParam("category") String category, Model model){
        List<Product> list = this.productService.getAllByCategory(Category.valueOf(category));
        model.addAttribute("products", list);
        return "main/index";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new UserForm());
        return "user/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult) {

        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors())
            return "user/registration";

        userService.saveUser(new User(userForm));

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "main/403";
    }

}
