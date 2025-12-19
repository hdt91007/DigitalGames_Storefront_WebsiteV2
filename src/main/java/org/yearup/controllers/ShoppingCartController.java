package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("cart")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class ShoppingCartController {

   final private ShoppingCartDao shoppingCartDao;
    final private UserDao userDao;
    final private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
}
    // each method in this controller requires a Principal object as a parameter
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            // find database user by userId
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            return shoppingCartDao.getByUserId(user.getId());
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "34 Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("products/{productId}")
    @PreAuthorize("hasRole('ROLE_USER')")
public ShoppingCart addProducts(@PathVariable int productId, Principal principal){
        try {
            String username = principal.getName();
            User user = userDao.getByUserName(username);
            shoppingCartDao.addProductsToCart(user.getId(), productId, 1);
            return shoppingCartDao.getByUserId(user.getId());
        }
            catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "15 Oops... our bad.");
        }
    }


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("products/{productId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void  updateProductInCart(@PathVariable int productId, @RequestBody ShoppingCartItem item,  Principal principal){
        try {
            String username = principal.getName();
            User user = userDao.getByUserName(username);
            shoppingCartDao.update(user.getId(),productId, item.getQuantity());
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "12 Oops... our bad.");
        }
    }

    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_USER')")
public void emptyCart (Principal principal ){
        try{
            String username = principal.getName();
            User user = userDao.getByUserName(username);
            shoppingCartDao.deleteCart(user.getId());
            System.out.println("Cart was Emptied for user "+ username);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "13 Oops... our bad.");
        }
    }
}
