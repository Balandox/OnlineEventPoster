package org.suai.courceWork.models.entities;

import lombok.*;
import org.suai.courceWork.dto.BucketItemDTO;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Order_details")
public class OrderDetails {
    // это сведение о товаре в заказе (в корзине)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int price;

    @Column(name = "total_sum")
    private int totalSum;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    //+

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    //+

    public OrderDetails(BucketItemDTO bucketItemDTO){
        this.product = bucketItemDTO.getProduct();
        this.totalSum = bucketItemDTO.calculateTotalSum();
        this.price = bucketItemDTO.getProduct().getPrice();
        this.quantity = bucketItemDTO.getQuantity();
    }

}
