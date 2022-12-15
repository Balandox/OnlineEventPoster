package org.suai.courceWork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.suai.courceWork.models.entities.Bucket;
import org.suai.courceWork.models.entities.Product;

import java.util.List;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Integer> {
    void deleteBucketById(int id);
}
