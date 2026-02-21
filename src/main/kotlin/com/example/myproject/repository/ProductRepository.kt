package com.example.myproject.repository

import com.example.myproject.domain.Product
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository

@Repository
class ProductRepository(
    private val jdbcClient: JdbcClient
) {

    fun save(product: Product) {
        jdbcClient.sql(
            """
            INSERT INTO products (id, title, vendor, product_type, price)
            VALUES (:id, :title, :vendor, :productType, :price)
            ON CONFLICT (id) DO NOTHING
            """
        )
            .param("id", product.id)
            .param("title", product.title)
            .param("vendor", product.vendor)
            .param("productType", product.productType)
            .param("price", product.price)
            .update()
    }

    fun findAll(): List<Product> {
        return jdbcClient.sql(
            """
            SELECT id, title, vendor, product_type, price
            FROM products
            ORDER BY id
            LIMIT 50
            """
        )
            .query { rs, _ ->
                Product(
                    id = rs.getLong("id"),
                    title = rs.getString("title"),
                    vendor = rs.getString("vendor"),
                    productType = rs.getString("product_type"),
                    price = rs.getDouble("price")
                )
            }
            .list()
    }


    fun findByTitleContaining(query: String): List<Product> {
        val pattern = "%${query.trim().replace("%", "\\%")}%"
        return jdbcClient.sql(
            """
        SELECT id, title, vendor, product_type, price
        FROM products
        WHERE title ILIKE :pattern
        ORDER BY title
        LIMIT 50
        """
        )
            .param("pattern", pattern)
            .query(::mapRow)
            .list()
    }

    private fun mapRow(rs: java.sql.ResultSet, rowNum: Int) = Product(
        id = rs.getLong("id"),
        title = rs.getString("title"),
        vendor = rs.getString("vendor"),
        productType = rs.getString("product_type"),
        price = rs.getDouble("price")
    )

    fun findById(id: Long): Product? {
        return jdbcClient.sql(
            """
        SELECT id, title, vendor, product_type, price
        FROM products WHERE id = :id
        """
        )
            .param("id", id)
            .query(::mapRow)
            .optional()
            .orElse(null)
    }

    fun update(product: Product) {
        jdbcClient.sql(
            """
        UPDATE products
        SET title = :title, vendor = :vendor, product_type = :productType, price = :price
        WHERE id = :id
        """
        )
            .param("id", product.id)
            .param("title", product.title)
            .param("vendor", product.vendor)
            .param("productType", product.productType)
            .param("price", product.price)
            .update()
    }
}



