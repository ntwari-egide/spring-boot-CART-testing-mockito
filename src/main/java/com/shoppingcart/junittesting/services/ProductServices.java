package com.shoppingcart.junittesting.services;

import com.shoppingcart.junittesting.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductServices {

    public List<Product> getAll();

    public Product getProductById();

}
