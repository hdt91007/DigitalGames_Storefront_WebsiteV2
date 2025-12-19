package org.yearup.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here

    void addProductsToCart (int userID, int productId, int quantity);
    void update (int userID, int productId, int quantity);
    void deleteCart (int userID);
}
