package org.suai.courceWork.conrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.suai.courceWork.dto.BucketDTO;
import org.suai.courceWork.dto.BucketItemDTO;
import org.suai.courceWork.models.entities.Bucket;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.services.BucketService;
import org.suai.courceWork.services.ProductService;
import org.suai.courceWork.services.UserService;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ProductService productService;

    private final BucketService bucketService;

    @Autowired
    public UserController(UserService userService, ProductService productService, BucketService bucketService){
        this.userService = userService;
        this.productService = productService;
        this.bucketService = bucketService;
    }

    @GetMapping("/buyProduct")
    public String buyProduct(@RequestParam("productId") int id, RedirectAttributes atr , Model model) throws Exception {

        Product product = productService.getProductById(id);
        this.bucketService.addProductToBucket(product, this.userService.getPrincipalName());

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
    public String bucketUpdateQuantity(@ModelAttribute("bucketDTO") BucketDTO bucketDTO, RedirectAttributes attributes){
        for(BucketItemDTO itemDTO : bucketDTO.getBucketItems()){
            System.out.println(itemDTO.getQuantity());
        }
        return "redirect:/user/bucket";
    }

}
