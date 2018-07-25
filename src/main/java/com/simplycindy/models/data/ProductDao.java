package com.simplycindy.models.data;

import com.simplycindy.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ProductDao extends CrudRepository<Product, Integer> {

}
