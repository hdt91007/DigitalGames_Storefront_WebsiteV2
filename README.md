# Capstone- DigitalGames E-commerse API


## Description  
The focus of this project was to work with an incomplete backend and api for a ficitonal videogame store by the name of DGS(Digital Games Storefront) to help with testing/bugfixing both the front end and back end of a website as well as build out certain functionality, Javascript, SQL, Postman, and SpringBoot

##  Features
- **Varying Role Access**: Create, update, and delete products and categories for those with admin role, whereas users can search, and add products into customer carts for checkout.
- **Search Function**: the ability to search based off Category_Id, Pricing, and Sub-Category on backend w/ inuitive filter on frontend 

- **Shopping Cart**: Add products to cart, update quantities, and manage items

## Backend Project Structure

```
src/main/java/org/yearup/
├── controllers/          # REST API endpoints
│   ├── ProductsController.java
│   ├── CategoriesController.java
│   └── ShoppingCartController.java
├── data/                 # Data access layer
│   ├── mysql/           # MySQL implementations
│   │   ├── MySqlProductDao.java
│   │   ├── MySqlCategoryDao.java
│   │   └── MySqlShoppingCartDao.java
│   └── interfaces/      # DAO interfaces
├── models/              # Domain models
│   ├── Product.java
│   ├── Category.java
│   ├── ShoppingCart.java
│   └── ShoppingCartItem.java
└── security/            # Security configuration
```

## Interesting Bit of Code
-Bug 1 solution using null and dynamic parameterindex  
<img width="1282" height="810" alt="image" src="https://github.com/user-attachments/assets/daa0dcf3-8e7c-4d3f-b5a3-09fe9f638a64" />

## Future Features
- Implementation of User Wishlist for out of sale quantity products
- Frontend Accessibility allowing for CRUD for adminstrative rolls, checkout, registration of users.
- Multicategory entries for i.e. accessories for specific consoles
- Use of an api database to pull current pricing and adjustments based of recent sales
- Integration w/ inventory intake system
