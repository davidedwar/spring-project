CREATE OR REPLACE VIEW chef_average_rating AS
SELECT
    o.chef_id,
    AVG(r.rating) AS average_rating
FROM
    order_rating r
JOIN orders o ON r.order_id = o.id
GROUP BY
    o.chef_id;
