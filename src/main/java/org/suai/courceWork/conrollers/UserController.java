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

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final ProductServiceImpl productServiceImpl;

    private final BucketServiceImpl bucketServiceImpl;

    private final OrderServiceImpl orderServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, ProductServiceImpl productServiceImpl, BucketServiceImpl bucketServiceImpl, OrderServiceImpl orderServiceImpl){
        this.userServiceImpl = userServiceImpl;
        this.productServiceImpl = productServiceImpl;
        this.bucketServiceImpl = bucketServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
    }

    @GetMapping("/buyProduct")
    public String buyProduct(@RequestParam("productId") int id, RedirectAttributes atr) throws Exception {

        try {
            Product product = productServiceImpl.getProductById(id);
            this.bucketServiceImpl.addProductToBucket(product, this.userServiceImpl.getPrincipalName());
        }
        catch (ArithmeticException e){
            atr.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/bucket")
    public String showBucket(Model model) throws Exception {

        BucketDTO bucketDTO = this.bucketServiceImpl.getBucketByUserName(this.userServiceImpl.getPrincipalName());

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
        this.bucketServiceImpl.clearBucket(this.userServiceImpl.getPrincipalName());
        return "redirect:/user/bucket";
    }

    @GetMapping("/bucket/addProduct")
    public String addOneCopyToCart(@RequestParam("productId") int id, RedirectAttributes atr) throws Exception {
        try {
            Product product = productServiceImpl.getProductById(id);
            this.bucketServiceImpl.addProductToBucket(product, this.userServiceImpl.getPrincipalName());
        }
        catch (ArithmeticException e){
            atr.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/user/bucket";
    }

    @GetMapping("/bucket/reduceProduct")
    public String reduceQuantityOfProductInBucket(@RequestParam("productId") int id) throws Exception {
        Product product = productServiceImpl.getProductById(id);
        this.bucketServiceImpl.reduceQuantityOfProduct(product, this.userServiceImpl.getPrincipalName());

        return "redirect:/user/bucket";
    }

    @GetMapping("/bucket/removeProduct")
    public String deleteProductFromBucket(@RequestParam("productId") int id) throws Exception {
        Product product = productServiceImpl.getProductById(id);
        this.bucketServiceImpl.deleteProductFromBucket(product, this.userServiceImpl.getPrincipalName());

        return "redirect:/user/bucket";
    }

    @GetMapping("/bucketConfirmation")
    public String previewOrder(Model model) throws Exception {

        String userName = this.userServiceImpl.getPrincipalName();

        BucketDTO bucketDTO = this.bucketServiceImpl.getBucketByUserName(userName);
        bucketDTO.setUser(this.userServiceImpl.findFirstByName(userName));

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

        Order order = orderServiceImpl.saveOrder(this.userServiceImpl.getPrincipalName());

        //this.bucketService.clearBucket(this.userService.getPrincipalName());

        model.addAttribute("order", order);

        return "user/bucketFinalize";
    }

}
