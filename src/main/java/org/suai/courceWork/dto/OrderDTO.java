package org.suai.courceWork.dto;

import lombok.*;
import org.suai.courceWork.models.entities.Order;
import org.suai.courceWork.models.entities.OrderDetails;
import org.suai.courceWork.models.entities.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Order order;
    private int totalAmountOfTicketsAtOrder;

    public void calculateAmount(){
        for(OrderDetails orderDetails : this.order.getOrderDetails()){
            totalAmountOfTicketsAtOrder += orderDetails.getQuantity();
        }
    }

}
