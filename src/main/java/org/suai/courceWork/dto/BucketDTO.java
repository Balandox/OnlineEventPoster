package org.suai.courceWork.dto;

import lombok.*;
import org.suai.courceWork.models.entities.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDTO {
    private int amountOfProducts;
    private int sum;

    private User user;

    private List<BucketItemDTO> bucketItems = new ArrayList<>();

    public int getTotalBucketSum(){
        for(BucketItemDTO bucketItemDTO : bucketItems)
            this.sum += bucketItemDTO.calculateTotalSum();

        return this.sum;
    }

    public int getTotalAmount(){
        for(BucketItemDTO bucketItemDTO : bucketItems)
            this.amountOfProducts += bucketItemDTO.getQuantity();

        return this.amountOfProducts;
    }

   public void calculate(){
        this.amountOfProducts = this.getTotalAmount();

        for(BucketItemDTO bucketItemDTO : bucketItems)
            this.sum += bucketItemDTO.calculateTotalSum();
    }

    public void updateQuantity(BucketDTO bucketDTO) {
        if (bucketDTO != null) {
            //данные, полученные из формы
            List<BucketItemDTO> items = bucketDTO.getBucketItems();
            for (BucketItemDTO newItem : items) {
                for(BucketItemDTO oldItem : this.getBucketItems()){
                    if(newItem.equals(oldItem))
                        oldItem.setQuantity(newItem.getQuantity());
                }
            }
        }

    }

}
