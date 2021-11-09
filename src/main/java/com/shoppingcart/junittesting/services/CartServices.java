package com.shoppingcart.junittesting.services;

import com.shoppingcart.junittesting.model.Cart;
import com.shoppingcart.junittesting.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartServices {

    public List<Cart> getAll();

    public Cart save(Cart newCart);

    public Cart addNewProduct(Long cartId, Long productId);

    public Cart remoteItem(Long cartId, Long productId);

    public Cart cartDetails(Long cartId);

}
