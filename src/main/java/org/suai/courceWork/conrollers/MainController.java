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
import org.suai.courceWork.services.implementations.BucketServiceImpl;
import org.suai.courceWork.services.implementations.ProductServiceImpl;
import org.suai.courceWork.services.implementations.UserServiceImpl;
import org.suai.courceWork.utils.UserValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

    private final ProductServiceImpl productServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final BucketServiceImpl bucketServiceImpl;
    private final UserValidator userValidator;

    @Autowired
    public MainController(ProductServiceImpl productServiceImpl, UserServiceImpl userServiceImpl, BucketServiceImpl bucketServiceImpl, UserValidator userValidator) {
        this.productServiceImpl = productServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.bucketServiceImpl = bucketServiceImpl;
        this.userValidator = userValidator;
    }

    @GetMapping({"", "/"})
    public String index(@RequestParam(value = "category", required = false) String category,
            @RequestParam(value="search", required = false) String searchProduct,
            @RequestParam(value="date", required = false) String date,
            Model model){

/*        category  seacrh
          null      !null  // просто дефолт поиск
          !null     null   // дефолт категория
          null      null   // дефолт страница
          !null     !null  // поиск в категории
          */

        List<Product> list = null;

       if(category == null && searchProduct == null && date == null)
            list = this.productServiceImpl.getAll();

        else if(category != null && searchProduct == null)
            list = this.productServiceImpl.getAllByCategory(Category.valueOf(category));

        else if (category == null && searchProduct != null)
            list = this.productServiceImpl.searchProductByTitle(searchProduct);

        else if(category != null && searchProduct != null)
            list = this.productServiceImpl.searchProductWithCategory(Category.valueOf(category), searchProduct);

        else if(date != null)
            list = this.productServiceImpl.getAllByDate(date);


        model.addAttribute("search", searchProduct);
        model.addAttribute("category", category);
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

        userServiceImpl.saveUser(new User(userForm));

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() throws Exception {
        return "user/login";
    }


    @GetMapping("/403")
    public String accessDenied() {
        return "main/403";
    }

}
