package org.suai.courceWork.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suai.courceWork.dto.BucketDTO;
import org.suai.courceWork.dto.BucketItemDTO;
import org.suai.courceWork.models.entities.Bucket;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.repositories.BucketRepository;
import org.suai.courceWork.repositories.ProductRepository;
import org.suai.courceWork.services.interfaces.BucketService;

import java.util.*;

@Service
@Transactional
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserServiceImpl userServiceImpl;


    @Autowired
    public BucketServiceImpl(BucketRepository bucketRepository, ProductRepository productRepository, UserServiceImpl userServiceImpl) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userServiceImpl = userServiceImpl;
    }

    public void addProductToBucket(Product product, String name) throws Exception {

        User user = this.userServiceImpl.findFirstByName(name);

        if (product.getAmount() - 1 < 0) {
            throw new ArithmeticException("К сожалению, все билеты распроданы!");
        }

        product.setAmount(product.getAmount() - 1);

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

    }

    public void reduceQuantityOfProduct(Product product, String name){
        User user = this.userServiceImpl.findFirstByName(name);
        Bucket bucket = user.getBucket();
        bucket.getProductList().remove(product);
        product.setAmount(product.getAmount() + 1);
    }

    public void deleteProductFromBucket(Product product, String name){
        User user = this.userServiceImpl.findFirstByName(name);
        Bucket bucket = user.getBucket();
        int amountToReturn = 0;

        for(Product tmpProduct : bucket.getProductList())
            if(tmpProduct.equals(product))
                amountToReturn++;

        product.setAmount(product.getAmount() + amountToReturn);
        bucket.getProductList().removeAll(this.productRepository.findAllByTitle(product.getTitle()));
    }

    public void clearBucket(String name){
        User user = this.userServiceImpl.findFirstByName(name);
        Bucket bucket = user.getBucket();

        List<Product> productList = bucket.getProductList();
        List<Product> tmpProductList = new ArrayList<>();


        for(Product tmpProduct : productList)
            //чтобы выбросить повторы и правильно возвращать товары в базу
            if(!tmpProductList.contains(tmpProduct)){
                tmpProductList.add(tmpProduct);
                tmpProduct.setAmount(tmpProduct.getAmount() + Collections.frequency(productList, tmpProduct));
            }

        bucket.getProductList().clear();
        this.bucketRepository.deleteBucketById(bucket.getId());
        user.setBucket(null);
    }

    public void deleteBucketAfterOrder(String name) {
        User user = this.userServiceImpl.findFirstByName(name);
        Bucket bucket = user.getBucket();

        bucket.getProductList().clear();
        this.bucketRepository.deleteBucketById(bucket.getId());
        user.setBucket(null);
    }


    public BucketDTO getBucketByUserName(String name){
        User user = this.userServiceImpl.findFirstByName(name);

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
