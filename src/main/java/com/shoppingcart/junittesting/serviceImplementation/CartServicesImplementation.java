package com.shoppingcart.junittesting.serviceImplementation;

import com.shoppingcart.junittesting.model.Cart;
import com.shoppingcart.junittesting.model.Product;
import com.shoppingcart.junittesting.repositories.CartRepository;
import com.shoppingcart.junittesting.repositories.ProductRepository;
import com.shoppingcart.junittesting.services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServicesImplementation implements CartServices {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Cart> getAll() {

        List<Cart> cartList = cartRepository.findAll();


        for (Cart cart: cartList) {
            Double totalCost  = 0d;

            for (Product product: cart.getProductList()){
                totalCost += product.getTotalCost();
            }

            cart.setTotalCost(totalCost);

        }

        return cartList;
    }

    @Override
    public Cart save(Cart newCart) {
        return cartRepository.save(newCart);
    }

    @Override
    public Cart addNewProduct(Long cartId, Long productId) {

        Optional<Cart> cartOptional = cartRepository.findById(cartId);

        Optional<Product> productOptional = productRepository.findById(productId);

        if (cartOptional.isPresent() && productOptional.isPresent()) {

            //adding product to cart
            cartOptional.get().getProductList().add(productOptional.get());

            Double totalCost = 0d;

            for (Product product : cartOptional.get().getProductList()) {
                totalCost += product.getTotalCost();
            }

            cartOptional.get().setTotalCost(totalCost);

        }

        return cartRepository.save(cartOptional.get());
    }

    @Override
    public Cart remoteItem(Long cartId, Long productId) {

        Optional<Cart> cartOptional = cartRepository.findById(cartId);

        Optional<Product> productOptional = productRepository.findById(productId);

        if (cartOptional.isPresent() && productOptional.isPresent()) {

            //removing product from cart
            cartOptional.get().getProductList().remove(productOptional.get());

            Double totalCost = 0d;

            for (Product product : cartOptional.get().getProductList()) {
                totalCost += product.getTotalCost();
            }

            cartOptional.get().setTotalCost(totalCost);

        }

        return cartRepository.save(cartOptional.get());

    }

    @Override
    public Cart cartDetails(Long cartId) {

        Optional<Cart> cartOptional = cartRepository.findById(cartId);

        Double totalCost = 0d;

        for (Product product : cartOptional.get().getProductList()) {
            totalCost += product.getTotalCost();

            cartOptional.get().setTotalCost(totalCost);
        }



        return cartOptional.get();
    }
}
