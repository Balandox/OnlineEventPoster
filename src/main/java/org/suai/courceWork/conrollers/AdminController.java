package org.suai.courceWork.conrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.suai.courceWork.dto.OrderDTO;
import org.suai.courceWork.models.entities.Order;
import org.suai.courceWork.services.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderService orderService;

    @Autowired
    public AdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orderList")
    public String getOrderList(Model model){
        model.addAttribute("orderList", this.orderService.getAllOrderDTO());

        return "admin/orderList";
    }

    @GetMapping("/orderInfo")
    public String orderView(@RequestParam("orderId") int orderId, Model model){
        OrderDTO orderDTO = this.orderService.findOrderById(orderId);
        model.addAttribute("orderInfo", orderDTO);
        return "admin/orderInfo";
    }

/*    @GetMapping("/productEdit")
    public String editProduct(@RequestParam("productId") int productId, Model model){
        //model.addAttribute()
    }*/

}
