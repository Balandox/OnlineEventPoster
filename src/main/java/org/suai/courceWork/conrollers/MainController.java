package org.suai.courceWork.conrollers;

import cn.apiclub.captcha.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.suai.courceWork.models.entities.ConfirmationToken;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.models.forms.UserForm;
import org.suai.courceWork.models.enums.Category;
import org.suai.courceWork.services.interfaces.*;
import org.suai.courceWork.utils.CaptchaUtil;
import org.suai.courceWork.utils.UserValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

    private final ProductService productService;
    private final UserService userService;
    private final BucketService bucketService;
    private final UserValidator userValidator;

    private final EmailSenderService emailSenderService;

    private final ConfirmationTokenService confirmationTokenService;


    @Autowired
    public MainController(ProductService productService, UserService userService, BucketService bucketService,
                          UserValidator userValidator, EmailSenderService emailSenderService, ConfirmationTokenService confirmationTokenService) {
        this.productService = productService;
        this.userService = userService;
        this.bucketService = bucketService;
        this.userValidator = userValidator;
        this.emailSenderService = emailSenderService;
        this.confirmationTokenService = confirmationTokenService;
    }
    @GetMapping({"", "/"})
    public String index(@RequestParam(value = "category", required = false) String category,
            @RequestParam(value="search", required = false) String searchProduct,
            @RequestParam(value="date", required = false) String date,
            @RequestParam(value = "topFive", required = false) boolean topFive,
            Model model){

/*        category  seacrh
          null      !null  // просто дефолт поиск
          !null     null   // дефолт категория
          null      null   // дефолт страница
          !null     !null  // поиск в категории
          */
        System.out.println(topFive);
        List<Product> list = null;

        if(topFive)
            list = this.productService.getTopFiveEvents();

        else if (category == null && searchProduct == null && date == null)
            list = this.productService.getAll();

        else if(category != null && searchProduct == null)
            list = this.productService.getAllByCategory(Category.valueOf(category));

        else if (category == null && searchProduct != null)
            list = this.productService.searchProductByTitle(searchProduct);

        else if(category != null && searchProduct != null)
            list = this.productService.searchProductWithCategory(Category.valueOf(category), searchProduct);

        else if(date != null)
            list = this.productService.getAllByDate(date);


        model.addAttribute("search", searchProduct);
        model.addAttribute("category", category);
        model.addAttribute("products", list);
        return "main/index";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        getCaptcha(userForm);
        return "user/registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid @ModelAttribute("userForm") UserForm userForm, Model model, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if(bindingResult.hasErrors()) {
            getCaptcha(userForm);
            model.addAttribute("userForm", userForm);
            return "user/registration";
        }

        User user = new User(userForm);
        userService.saveUser(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        this.confirmationTokenService.saveConfirmationToken(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("semen221411@mail.ru");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8081/confirmAccount?token=" + confirmationToken.getConfirmationToken());
        emailSenderService.sendEmail(mailMessage);
        model.addAttribute("email", user.getEmail());

        return "redirect:/";
    }

    @RequestMapping(value="/confirmAccount", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(Model model, @RequestParam("token") String confirmationToken)
    {
        ConfirmationToken token = this.confirmationTokenService.findByConfirmationToken(confirmationToken);
        User user = this.userService.findByEmail(token.getUser().getEmail());
        user.setEnabled(true);
        this.userService.saveUser(user);

        return "user/accountVerified";
    }

    @GetMapping("/login")
    public String login() throws Exception {
        return "user/login";
    }


    @GetMapping("/403")
    public String accessDenied() {
        return "main/403";
    }

    private void getCaptcha(UserForm userForm) {
        Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
        userForm.setHiddenCaptcha(captcha.getAnswer());
        userForm.setCaptcha(""); // value entered by the User
        userForm.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
    }

}
