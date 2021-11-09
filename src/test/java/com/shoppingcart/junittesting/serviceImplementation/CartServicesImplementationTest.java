package com.shoppingcart.junittesting.serviceImplementation;

import com.shoppingcart.junittesting.model.Cart;
import com.shoppingcart.junittesting.model.Product;
import com.shoppingcart.junittesting.repositories.CartRepository;
import com.shoppingcart.junittesting.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.PushBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServicesImplementationTest {

    @Mock
    CartRepository cartRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    CartServicesImplementation cartServicesImplementation;

    @Test
    public  void createShoppingCart(){

        List<Product> productList = new ArrayList<>();

        productList.add(
                new Product("shoes",4, 500d, 2000d)
        );

        when(cartRepository.save(ArgumentMatchers.any(Cart.class))).thenReturn(
                new Cart(productList,2000d)
        );

        assertEquals(2000d,cartServicesImplementation.save(new Cart(productList,2000d)).getTotalCost());

    }


    @Test
    public void returnAllCarts(){
        List<Product> productList = new ArrayList<>();

        productList.add(
                new Product("shoes",4, 500d, 2000d)
        );

        when(cartRepository.findAll()).thenReturn(Arrays.asList(
                new Cart(1l,productList,2000d),
                new Cart(2l,productList,2300d)
                ));


        assertThat(cartRepository.findAll().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void checkEmptyCart(){

        List<Product> productList = new ArrayList<>();

        Cart defaultCart = new Cart(1l,productList,0d);

        when(cartRepository.findById(defaultCart.getId())).thenReturn(Optional.of(defaultCart));

        assertEquals(0, cartServicesImplementation.cartDetails(defaultCart.getId()).getTotalCost());

    }

    @Test
    public void addNewProduct () {

        Product product = new Product(1l,"t-shirt",2, 1000d, 2000d);

        List<Product> productList = new ArrayList<>();

        productList.add(
                new Product(1l,"shoes",4, 500d, 2000d)
        );

        Cart defaultCart = new Cart(1l,productList,2000d);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        when(cartRepository.findById(defaultCart.getId())).thenReturn(Optional.of(defaultCart));

        assertEquals(0,
                cartServicesImplementation
                        .addNewProduct(defaultCart.getId(),product.getId())
                        .getProductList().size());

    }

    @Test
    public void removeProductFromCart () {

        Product product = new Product(1l,"shoes",4, 500d, 2000d);

        List<Product> productList = new ArrayList<>();

        productList.add(
                new Product(1l,"shoes",4, 500d, 2000d)
        );

        Cart defaultCart = new Cart(1l,productList,2000d);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        when(cartRepository.findById(defaultCart.getId())).thenReturn(Optional.of(defaultCart));

        assertThat(
                cartServicesImplementation
                        .remoteItem(defaultCart.getId(),product.getId())
                        .getProductList().size())
                .isEqualTo(0);

    }

}
