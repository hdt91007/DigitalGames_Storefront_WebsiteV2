SELECT *FROM shopping_cart as sc
left join products as p on sc.product_id = p.product_id
where sc.user_id = ?