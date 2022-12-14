package org.suai.courceWork.dto;

import lombok.*;
import org.suai.courceWork.models.entities.Product;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class BucketItemDTO {
    private Product product;
    private int quantity;
    private int sum;

    public BucketItemDTO(Product product){
        this.product = product;
        this.quantity = 1;
        this.sum = product.getPrice();
    }

    public int calculateTotalSum(){
        return this.product.getPrice() * this.quantity;
    }


}
