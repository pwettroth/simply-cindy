package com.simplycindy.models.data;

import com.simplycindy.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public interface ProductDao extends CrudRepository<Product, Integer> {

    default List<Product> findAll(int categoryId) {
        List<Product> products = new ArrayList<Product>();
        for(Product product : this.findAll()) {
            if (product.getCategory() != null && categoryId == product.getCategory().getId()) {
                products.add(product);
            }
        }
        return products;
    }
}
