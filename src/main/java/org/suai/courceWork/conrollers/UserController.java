package org.suai.courceWork.conrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.suai.courceWork.dto.BucketDTO;
import org.suai.courceWork.models.entities.Order;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.services.implementations.BucketServiceImpl;
import org.suai.courceWork.services.implementations.OrderServiceImpl;
import org.suai.courceWork.services.implementations.ProductServiceImpl;
import org.suai.courceWork.services.implementations.UserServiceImpl;
import org.suai.courceWork.services.interfaces.BucketService;
import org.suai.courceWork.services.interfaces.OrderService;
import org.suai.courceWork.services.interfaces.ProductService;
import org.suai.courceWork.services.interfaces.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ProductService productService;

    private final BucketService bucketService;

    private final OrderService orderService;

    @Autowired
    public UserController(UserService userService, ProductService productService, BucketService bucketService, OrderService orderService){
        this.userService = userService;
        this.productService = productService;
        this.bucketService = bucketService;
        this.orderService = orderService;
    }

    @GetMapping("/buyProduct")
    public String buyProduct(@RequestParam("productId") int id, RedirectAttributes atr) throws Exception {

        try {
            Product product = productService.getProductById(id);
            this.bucketService.addProductToBucket(product, this.userService.getPrincipalName());
        }
        catch (ArithmeticException e){
            atr.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/bucket")
    public String showBucket(Model model) throws Exception {

        BucketDTO bucketDTO = this.bucketService.getBucketByUserName(this.userService.getPrincipalName());

        if(bucketDTO.getAmountOfProducts() == 0) {
            model.addAttribute("isEmpty", true);
        }
        else {
            model.addAttribute("bucket", bucketDTO);
            model.addAttribute("isEmpty", false);
        }
        return "user/bucket";
    }

    @PostMapping("/bucket")
    public String clearBucket() throws Exception {
        this.bucketService.clearBucket(this.userService.getPrincipalName());
        return "redirect:/user/bucket";
    }

    @GetMapping("/bucket/addProduct")
    public String addOneCopyToCart(@RequestParam("productId") int id, RedirectAttributes atr) throws Exception {
        try {
            Product product = productService.getProductById(id);
            this.bucketService.addProductToBucket(product, this.userService.getPrincipalName());
        }
        catch (ArithmeticException e){
            atr.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/user/bucket";
    }

    @GetMapping("/bucket/reduceProduct")
    public String reduceQuantityOfProductInBucket(@RequestParam("productId") int id) throws Exception {
        Product product = productService.getProductById(id);
        this.bucketService.reduceQuantityOfProduct(product, this.userService.getPrincipalName());

        return "redirect:/user/bucket";
    }

    @GetMapping("/bucket/removeProduct")
    public String deleteProductFromBucket(@RequestParam("productId") int id) throws Exception {
        Product product = productService.getProductById(id);
        this.bucketService.deleteProductFromBucket(product, this.userService.getPrincipalName());

        return "redirect:/user/bucket";
    }

    @GetMapping("/bucketConfirmation")
    public String previewOrder(Model model) throws Exception {

        String userName = this.userService.getPrincipalName();

        BucketDTO bucketDTO = this.bucketService.getBucketByUserName(userName);
        bucketDTO.setUser(this.userService.findFirstByName(userName));

        if(bucketDTO.getAmountOfProducts() == 0) {
            return "redirect:/user/bucket";
        }
        else{
            model.addAttribute("bucket", bucketDTO);
            return "user/bucketConfirmation";
        }
    }

    @PostMapping("/bucketConfirmation")
    public String confirmOrder(Model model) throws Exception {

        Order order = orderService.saveOrder(this.userService.getPrincipalName());

        model.addAttribute("order", order);

        return "user/bucketFinalize";
    }

}
