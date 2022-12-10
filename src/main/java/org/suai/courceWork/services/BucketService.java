package org.suai.courceWork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suai.courceWork.dto.BucketDTO;
import org.suai.courceWork.dto.BucketItemDTO;
import org.suai.courceWork.models.entities.Bucket;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.repositories.BucketRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BucketService {

    private BucketRepository bucketRepository;
    private UserService userService;

    @Autowired
    public BucketService(BucketRepository bucketRepository, UserService userService) {
        this.bucketRepository = bucketRepository;
        this.userService = userService;
    }


    public void addProductToBucket(Product product, String name) throws Exception {

        User user = this.userService.findFirstByName(name);

        Bucket bucket = null;
        // если до этого корзины у пользователя не было
        if(user.getBucket() == null){
            bucket = new Bucket(user);
            bucket.getProductList().add(product);
            bucketRepository.save(bucket);
            user.setBucket(bucket);
        }
        else {
            bucket = user.getBucket();
            bucket.getProductList().add(product);
        }


/*        else {
            bucket = user.getBucket();
            Product productFromBucket = findProductInBucket(bucket, product);
            if(productFromBucket != null) {
                throw new Exception("Товар уже добавлен в корзину!");
            }
            else {
                bucket.getProductList().add(product);
            }
        }*/

    }

    public BucketDTO getBucketByUserName(String name){
        User user = this.userService.findFirstByName(name);

        if(user.getBucket() == null)
            return new BucketDTO();

        BucketDTO bucketDTO = new BucketDTO();

        Map<Integer, BucketItemDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProductList();

        for (Product product : products) {
            BucketItemDTO item = mapByProductId.get(product.getId());
            if(item == null){
                mapByProductId.put(product.getId(), new BucketItemDTO(product));
            }
            else {
                item.setQuantity(item.getQuantity() + 1);
                item.setSum(item.getSum() + product.getPrice());
            }
        }

        bucketDTO.setBucketItems(new ArrayList<>(mapByProductId.values()));
        bucketDTO.calculate();

        return bucketDTO;
        //return user.getBucket();
    }

    public Product findProductInBucket(Bucket bucket, Product product){
        List<Product> productList = bucket.getProductList();

        for(Product productTmp : productList){
            if(productTmp.equals(product))
                return productTmp;
        }

        return null;
    }

}
