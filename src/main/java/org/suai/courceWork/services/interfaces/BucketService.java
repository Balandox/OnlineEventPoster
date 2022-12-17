package org.suai.courceWork.services.interfaces;


import org.suai.courceWork.dto.BucketDTO;
import org.suai.courceWork.models.entities.Bucket;
import org.suai.courceWork.models.entities.Product;

public interface BucketService {

    void addProductToBucket(Product product, String name) throws Exception;

    void reduceQuantityOfProduct(Product product, String name);

    void deleteProductFromBucket(Product product, String name);

    void clearBucket(String name);

    void deleteBucketAfterOrder(String name);

    BucketDTO getBucketByUserName(String name);

    Product findProductInBucket(Bucket bucket, Product product);
}
