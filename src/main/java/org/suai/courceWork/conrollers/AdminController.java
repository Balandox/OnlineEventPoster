package org.suai.courceWork.conrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.suai.courceWork.dto.OrderDTO;
import org.suai.courceWork.models.forms.ProductForm;
import org.suai.courceWork.services.implementations.OrderServiceImpl;
import org.suai.courceWork.services.implementations.ProductServiceImpl;
import org.suai.courceWork.utils.DateParser;
import org.suai.courceWork.utils.ProductFormDateValidator;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderServiceImpl orderServiceImpl;

    private final ProductServiceImpl productServiceImpl;

    private final ProductFormDateValidator productFormDateValidator;

    @Autowired
    public AdminController(OrderServiceImpl orderServiceImpl, ProductServiceImpl productServiceImpl, ProductFormDateValidator productFormDateValidator) {
        this.orderServiceImpl = orderServiceImpl;
        this.productServiceImpl = productServiceImpl;
        this.productFormDateValidator = productFormDateValidator;
    }

    @GetMapping("/orderList")
    public String getOrderList(Model model){
        model.addAttribute("orderList", this.orderServiceImpl.getAllOrderDTO());

        return "admin/orderList";
    }

    @GetMapping("/orderInfo")
    public String orderView(@RequestParam("orderId") int orderId, Model model){
        OrderDTO orderDTO = this.orderServiceImpl.findOrderById(orderId);
        model.addAttribute("orderInfo", orderDTO);
        return "admin/orderInfo";
    }

    @GetMapping("/productEdit/{id}")
    public String editProduct(@PathVariable("id") int productId, Model model){
        model.addAttribute("new", false);
        model.addAttribute("productForm", this.productServiceImpl.getProductFormById(productId));
        return "admin/productEdit";
    }

    @PostMapping("/productEdit/{id}")
    public String saveEditedProduct(@PathVariable("id") int productId, @ModelAttribute("productForm") @Valid ProductForm productForm,
                                    BindingResult bindingResult){

        Date dateOfEvent = DateParser.getDateWithTime(productForm.getDateOfEvent());

        if(dateOfEvent == null)
            bindingResult.rejectValue("dateOfEvent", "", "Правильный формат даты: yyyy.MM.dd HH:mm");

        if(bindingResult.hasErrors())
            return "admin/productEdit";

        this.productServiceImpl.saveEditedProduct(productForm, productId, dateOfEvent);

        return "redirect:/";
    }

    @GetMapping("/createProduct")
    public String createProduct(Model model){
        model.addAttribute("productForm", new ProductForm());
        model.addAttribute("new", true);
        return "admin/productEdit";
    }


}
