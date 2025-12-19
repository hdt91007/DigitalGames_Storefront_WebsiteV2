package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
       String sql =" SELECT * FROM shopping_cart as sc"
               +" left join products as p on sc.product_id = p.product_id"
               +"        where sc.user_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet row = statement.executeQuery();

            while (row.next())
            {
                ShoppingCartItem item = new ShoppingCartItem();

                // Create product from result set
                Product product = MySqlProductDao.mapRow(row);
                item.setProduct(product);

                // Set quantity from shopping_cart table
                item.setQuantity(row.getInt("quantity"));

                shoppingCart.add(item);
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException("Shopping cart add issue",e);
        }

        return shoppingCart;
    }

    @Override
    public void addProductsToCart(int userID, int productId, int quantity) {
            String sql = "INSERT INTO shopping_cart (user_id, product_id, quantity) " +
                    "VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE quantity = quantity + ?";

            try (Connection connection = getConnection())
            {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, userID);
                statement.setInt(2, productId);
                statement.setInt(3, quantity);
                statement.setInt(4, quantity);

                statement.executeUpdate();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }


    @Override
    public void update(int userID, int productId, int quantity) {
        String sql = "UPDATE shopping_cart " +
                "SET quantity = ? " +
                "WHERE user_id = ? AND product_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, userID);
            statement.setInt(3, productId);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteCart(int userId)
    {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    }

